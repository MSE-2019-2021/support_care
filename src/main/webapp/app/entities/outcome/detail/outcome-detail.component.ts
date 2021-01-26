import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { saveAs } from 'file-saver';

import { IOutcome } from '../outcome.model';
import { DocumentService } from 'app/entities/document/service/document.service';
import { IDocument } from 'app/entities/document/document.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OutcomeDeleteDialogComponent } from 'app/entities/outcome/delete/outcome-delete-dialog.component';

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
  selector: 'custom-outcome-detail',
  templateUrl: './outcome-detail.component.html',
})
export class OutcomeDetailComponent implements OnInit {
  outcome: IOutcome | null = null;
  thumbCount: IThumbCount;
  feedbacks: IFeedback[];
  isSaving = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  dateFormat: string;

  constructor(
    private activatedRoute: ActivatedRoute,
    private documentService: DocumentService,
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
    this.activatedRoute.data.subscribe(({ outcome }) => {
      this.outcome = outcome;
    });
    this.loadThumbsAndFeedback();
  }

  downloadDocument(document: IDocument): void {
    this.documentService.download(document.id!).subscribe(file => {
      saveAs(file, document.title);
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(outcome: IOutcome): void {
    const modalRef = this.modalService.open(OutcomeDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.outcome = outcome;
  }

  protected loadThumbsAndFeedback(): void {
    this.feedbacks = [];
    this.page = 0;
    this.getThumbs();
    this.loadAllFeedbacks();
  }

  protected getThumbs(): void {
    this.thumbService.countThumbsFromEntity(EntityFeedback.OUTCOME, this.outcome?.id ?? 0).subscribe((res: HttpResponse<IThumbCount>) => {
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
      'entityType.equals': EntityFeedback.OUTCOME.valueOf(),
      'entityId.equals': this.outcome?.id,
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
    thumb.entityType = EntityFeedback.OUTCOME;
    thumb.entityId = this.outcome?.id;

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
          feedback.entityType = EntityFeedback.OUTCOME;
          feedback.entityId = this.outcome?.id;
          feedback.entityName = this.outcome?.name;
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

  redirectToLink(link?: string): void {
    if (link) {
      window.open(link, '_blank');
    }
  }
}
