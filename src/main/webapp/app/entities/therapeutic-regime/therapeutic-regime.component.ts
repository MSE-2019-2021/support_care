import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TherapeuticRegimeService } from './therapeutic-regime.service';
import { TherapeuticRegimeDeleteDialogComponent } from './therapeutic-regime-delete-dialog.component';

@Component({
  selector: 'jhi-therapeutic-regime',
  templateUrl: './therapeutic-regime.component.html',
})
export class TherapeuticRegimeComponent implements OnInit, OnDestroy {
  therapeuticRegimes: ITherapeuticRegime[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.therapeuticRegimes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.therapeuticRegimeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ITherapeuticRegime[]>) => this.paginateTherapeuticRegimes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.therapeuticRegimes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTherapeuticRegimes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITherapeuticRegime): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTherapeuticRegimes(): void {
    this.eventSubscriber = this.eventManager.subscribe('therapeuticRegimeListModification', () => this.reset());
  }

  delete(therapeuticRegime: ITherapeuticRegime): void {
    const modalRef = this.modalService.open(TherapeuticRegimeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.therapeuticRegime = therapeuticRegime;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTherapeuticRegimes(data: ITherapeuticRegime[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.therapeuticRegimes.push(data[i]);
      }
    }
  }
}
