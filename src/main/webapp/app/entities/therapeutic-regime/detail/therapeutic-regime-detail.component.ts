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
  isSaving = false;
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
  }

  protected loadFeedback(): void {
    this.getThumbs();
    this.loadAll();
  }

  protected getThumbs(): void {
    this.feedbackService
      .countFeedbacksFromEntity(EntityFeedback.THERAPEUTIC_REGIME, this.therapeuticRegime?.id ?? 0)
      .subscribe((res: HttpResponse<IThumb>) => {
        this.thumb = res.body ?? new Thumb();
      });
  }

  protected loadAll(): void {
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
      .subscribe((res: HttpResponse<IFeedback[]>) => {
        this.paginateFeedbacks(res.body, res.headers);
      });
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  trackId(index: number, item: IFeedback): number {
    return item.id!;
  }

  protected sort(): string[] {
    return ['id,asc'];
  }

  protected getCriteria(): {} {
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
    this.isSaving = true;
    const feedback = new Feedback();
    feedback.entityName = EntityFeedback.THERAPEUTIC_REGIME;
    feedback.entityId = this.therapeuticRegime?.id;

    // When click on Thumb Up
    if (thumbUp) {
      // If it was already UP, deactivate it (it will delete the feedback in database)
      if (this.thumb.thumb === true) {
        feedback.thumb = undefined;

        // Otherwise activate it
      } else {
        feedback.thumb = true;
      }
      this.saveFeedback(feedback);

      // When click on Thumb Down
    } else {
      // If it was already DOWN, deactivate it (it will delete the feedback in database)
      if (this.thumb.thumb === false) {
        feedback.thumb = undefined;
        this.saveFeedback(feedback);

        // Otherwise activate it
      } else {
        feedback.thumb = false;
        const modalRef = this.modalService.open(DefineReasonDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.feedback = feedback;
        // unsubscribe not needed because closed completes on modal close
        modalRef.closed.subscribe(() => {
          this.isSaving = false;
          this.loadFeedback();
        });
      }
    }
  }

  protected saveFeedback(feedback: IFeedback): void {
    // Update/Delete user Feedback
    this.feedbackService.manageFeedbackFromEntity(feedback).subscribe(
      () => {
        this.isSaving = false;
        this.loadFeedback();
      },
      () => {
        this.isSaving = false;
      }
    );
  }
}
