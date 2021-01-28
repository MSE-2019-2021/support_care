import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITherapeuticRegime } from '../therapeutic-regime.model';
import { TherapeuticRegimeDeleteDialogComponent } from '../delete/therapeutic-regime-delete-dialog.component';

import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { IThumb, Thumb } from 'app/entities/thumb/thumb.model';
import { IThumbCount, ThumbCount } from 'app/entities/thumb/thumb-count.model';
import { ThumbService } from 'app/entities/thumb/service/thumb.service';
import { IFeedback, Feedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';
import { DefineReasonDialogComponent } from 'app/entities/feedback/define-reason/define-reason-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

@Component({
  selector: 'custom-therapeutic-regime-detail',
  templateUrl: './therapeutic-regime-detail.component.html',
})
export class TherapeuticRegimeDetailComponent implements OnInit {
  therapeuticRegime: ITherapeuticRegime | null = null;
  thumbCount: IThumbCount;
  feedbacks: IFeedback[];
  isSaving = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  dateFormat: string;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    protected thumbService: ThumbService,
    protected feedbackService: FeedbackService,
    protected parseLinks: ParseLinks
  ) {
    this.thumbCount = new ThumbCount();
    this.feedbacks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.dateFormat = DATE_TIME_FORMAT;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.therapeuticRegime = therapeuticRegime;
    });
    this.loadThumbsAndFeedback();
  }

  previousState(): void {
    window.history.back();
  }

  delete(therapeuticRegime: ITherapeuticRegime): void {
    const modalRef = this.modalService.open(TherapeuticRegimeDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.therapeuticRegime = therapeuticRegime;
  }

  protected loadThumbsAndFeedback(): void {
    this.feedbacks = [];
    this.page = 0;
    this.getThumbs();
    this.loadAllFeedbacks();
  }

  protected getThumbs(): void {
    this.thumbService
      .countThumbsFromEntity(EntityFeedback.THERAPEUTIC_REGIME, this.therapeuticRegime?.id ?? 0)
      .subscribe((res: HttpResponse<IThumbCount>) => {
        this.thumbCount = res.body ?? new ThumbCount();
      });
  }

  protected loadAllFeedbacks(): void {
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
    this.loadAllFeedbacks();
  }

  trackId(index: number, item: IFeedback): number {
    return item.id!;
  }

  protected sort(): string[] {
    return ['id,asc'];
  }

  protected getCriteria(): {} {
    return {
      'entityType.equals': EntityFeedback.THERAPEUTIC_REGIME.valueOf(),
      'entityId.equals': this.therapeuticRegime?.id,
      'thumb.equals': false,
      'reason.specified': true,
      'solved.equals': false,
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

  manageThumb(thumbUp: boolean): void {
    this.isSaving = true;
    const thumb = new Thumb();
    thumb.entityType = EntityFeedback.THERAPEUTIC_REGIME;
    thumb.entityId = this.therapeuticRegime?.id;

    // When click on Thumb Up
    if (thumbUp) {
      // If it was already UP, deactivate it (it will delete the thumb in database)
      if (this.thumbCount.thumb === true) {
        thumb.thumb = undefined;

        // Otherwise activate it
      } else {
        thumb.thumb = true;
      }

      // When click on Thumb Down
    } else {
      // If it was already DOWN, deactivate it (it will delete the thumb in database)
      if (this.thumbCount.thumb === false) {
        thumb.thumb = undefined;

        // Otherwise activate it
      } else {
        thumb.thumb = false;
      }
    }
    this.saveThumb(thumb);
  }

  protected saveThumb(thumb: IThumb): void {
    // Update/Delete user Thumb
    this.thumbService.manageThumbFromEntity(thumb).subscribe(
      () => {
        // Give a feedback
        if (thumb.thumb === false) {
          const feedback = new Feedback();
          feedback.entityType = EntityFeedback.THERAPEUTIC_REGIME;
          feedback.entityId = this.therapeuticRegime?.id;
          feedback.entityName = this.therapeuticRegime?.name;
          const modalRef = this.modalService.open(DefineReasonDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
          modalRef.componentInstance.feedback = feedback;
          // unsubscribe not needed because closed completes on modal close
          modalRef.closed.subscribe(() => {
            this.isSaving = false;
            this.loadThumbsAndFeedback();
          });
        } else {
          this.isSaving = false;
          this.loadThumbsAndFeedback();
        }
      },
      () => {
        this.isSaving = false;
      }
    );
  }
}
