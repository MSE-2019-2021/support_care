import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITherapeuticRegime } from '../therapeutic-regime.model';
import { TherapeuticRegimeDeleteDialogComponent } from '../delete/therapeutic-regime-delete-dialog.component';

import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { IThumb, Thumb } from 'app/entities/feedback/thumb.model';
import { Feedback, IFeedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';
import { DefineReasonDialogComponent } from 'app/entities/feedback/define-reason/define-reason-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';

@Component({
  selector: 'custom-therapeutic-regime-detail',
  templateUrl: './therapeutic-regime-detail.component.html',
})
export class TherapeuticRegimeDetailComponent implements OnInit {
  therapeuticRegime: ITherapeuticRegime | null = null;
  thumb: IThumb;
  feedbacks: IFeedback[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    protected feedbackService: FeedbackService,
    protected parseLinks: ParseLinks
  ) {
    this.thumb = new Thumb();
    this.feedbacks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.therapeuticRegime = therapeuticRegime;
    });
    this.loadFeedback();
  }

  previousState(): void {
    window.history.back();
  }

  delete(therapeuticRegime: ITherapeuticRegime): void {
    const modalRef = this.modalService.open(TherapeuticRegimeDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.therapeuticRegime = therapeuticRegime;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }

  loadFeedback(): void {
    this.getThumbs();
    this.loadAll();
  }

  getThumbs(): void {
    this.isLoading = true;

    this.feedbackService.countFeedbacksFromEntity(EntityFeedback.THERAPEUTIC_REGIME, this.therapeuticRegime?.id ?? 0).subscribe(
      (res: HttpResponse<IThumb>) => {
        this.isLoading = false;
        this.thumb = res.body ?? new Thumb();
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  loadAll(): void {
    this.isLoading = true;

    this.feedbackService
      .query(
        Object.assign(
          {},
          {
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
          },
          this.getCriteria()
        )
      )
      .subscribe(
        (res: HttpResponse<ITherapeuticRegime[]>) => {
          this.isLoading = false;
          this.paginateFeedbacks(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  trackId(index: number, item: ITherapeuticRegime): number {
    return item.id!;
  }

  sort(): string[] {
    return ['id,asc'];
  }

  getCriteria(): {} {
    return {
      'entityName.equals': EntityFeedback.THERAPEUTIC_REGIME.valueOf(),
      'entityId.equals': this.therapeuticRegime?.id,
      'thumb.equals': false,
    };
  }

  protected paginateFeedbacks(data: IFeedback[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.feedbacks.push(data[i]);
      }
    }
  }

  manageFeedback(thumbUp: boolean): void {
    const userFeedback = new Feedback();

    // When click on Thumb Up
    if (thumbUp) {
      // If it was already UP, deactivate it (it will delete the feedback in database)
      if (this.thumb.thumb === true) {
        userFeedback.thumb = undefined;

        // Otherwise activate it
      } else {
        userFeedback.thumb = true;
      }

      // When click on Thumb Down
    } else {
      // If it was already DOWN, deactivate it (it will delete the feedback in database)
      if (this.thumb.thumb === false) {
        userFeedback.thumb = undefined;

        // Otherwise activate it
      } else {
        userFeedback.thumb = false;
        // TODO: in modal it should just update userFeedback.reason and userFeedback.anonym when confirmed
        const modalRef = this.modalService.open(DefineReasonDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.feedback = userFeedback;
        // unsubscribe not needed because closed completes on modal close
        modalRef.closed.subscribe();
      }
    }

    // Update/Delete user Feedback
    this.feedbackService.manageFeedbackFromEntity(userFeedback).subscribe(
      () => {
        this.isLoading = false;
        this.loadFeedback();
      },
      () => {
        this.isLoading = false;
      }
    );
  }
}
