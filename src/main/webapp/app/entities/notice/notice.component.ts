import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INotice } from 'app/shared/model/notice.model';

import { ITEMS_PER_PAGE } from 'app/core/config/pagination.constants';
import { NoticeService } from './notice.service';
import { NoticeDeleteDialogComponent } from './notice-delete-dialog.component';

@Component({
  selector: 'custom-notice',
  templateUrl: './notice.component.html',
})
export class NoticeComponent implements OnInit, OnDestroy {
  notices: INotice[];
  eventSubscriber?: Subscription;
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected noticeService: NoticeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
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

  handleSyncList(): void {
    this.reset();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNotices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INotice): number {
    return item.id!;
  }

  registerChangeInNotices(): void {
    this.eventSubscriber = this.eventManager.subscribe('noticeListModification', () => this.reset());
  }

  delete(notice: INotice): void {
    const modalRef = this.modalService.open(NoticeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.notice = notice;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateNotices(data: INotice[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.notices.push(data[i]);
      }
    }
  }
}
