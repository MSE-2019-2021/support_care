import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeedback } from '../feedback.model';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { FeedbackService } from '../service/feedback.service';
import { FeedbackDeleteDialogComponent } from '../delete/feedback-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

@Component({
  selector: 'custom-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss'],
})
export class FeedbackComponent implements OnInit {
  feedbacks: IFeedback[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;
  dateFormat: string;

  sortForm = this.fb.group({
    status: ['unsolved'],
    creationDate: ['newer'],
  });

  constructor(
    protected feedbackService: FeedbackService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks,
    private fb: FormBuilder
  ) {
    this.feedbacks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
    this.dateFormat = DATE_TIME_FORMAT;
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
        (res: HttpResponse<IFeedback[]>) => {
          this.isLoading = false;
          this.paginateFeedbacks(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.feedbacks = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFeedback): number {
    return item.id!;
  }

  deleteAllResolved(): void {
    const modalRef = this.modalService.open(FeedbackDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  sort(): string[] {
    const result = [];
    if (this.sortForm.get('status')!.value === 'solved') {
      result.push('solved,desc');
    } else {
      result.push('solved,asc');
    }
    if (this.sortForm.get('creationDate')!.value === 'older') {
      result.push('createdDate,desc');
    } else {
      result.push('createdDate,asc');
    }
    result.push('id');
    return result;
  }

  protected getCriteria(): {} {
    return {
      'reason.specified': true,
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

  markFeedbackAsSolved(feedback: IFeedback): void {
    feedback.solved = true;
    this.feedbackService.update(feedback).subscribe(() => {
      this.reset();
    });
  }
}
