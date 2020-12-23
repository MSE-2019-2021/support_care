import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INotice } from '../notice.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { NoticeService } from '../service/notice.service';
import { NoticeDeleteDialogComponent } from '../delete/notice-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'custom-notice',
  templateUrl: './notice.component.html',
})
export class NoticeComponent implements OnInit {
  notices: INotice[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected noticeService: NoticeService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.notices = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.noticeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<INotice[]>) => {
          this.isLoading = false;
          this.paginateNotices(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.notices = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INotice): number {
    return item.id!;
  }

  delete(notice: INotice): void {
    const modalRef = this.modalService.open(NoticeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.notice = notice;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateNotices(data: INotice[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.notices.push(data[i]);
      }
    }
  }
}
