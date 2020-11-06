import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IToxicityRate } from 'app/shared/model/toxicity-rate.model';

import { ITEMS_PER_PAGE } from 'app/core/config/pagination.constants';
import { ToxicityRateService } from './toxicity-rate.service';
import { ToxicityRateDeleteDialogComponent } from './toxicity-rate-delete-dialog.component';

@Component({
  selector: 'custom-toxicity-rate',
  templateUrl: './toxicity-rate.component.html',
})
export class ToxicityRateComponent implements OnInit, OnDestroy {
  toxicityRates: IToxicityRate[];
  eventSubscriber?: Subscription;
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected toxicityRateService: ToxicityRateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.toxicityRates = [];
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

    this.toxicityRateService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IToxicityRate[]>) => {
          this.isLoading = false;
          this.paginateToxicityRates(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.toxicityRates = [];
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
    this.registerChangeInToxicityRates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IToxicityRate): number {
    return item.id!;
  }

  registerChangeInToxicityRates(): void {
    this.eventSubscriber = this.eventManager.subscribe('toxicityRateListModification', () => this.reset());
  }

  delete(toxicityRate: IToxicityRate): void {
    const modalRef = this.modalService.open(ToxicityRateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.toxicityRate = toxicityRate;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateToxicityRates(data: IToxicityRate[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.toxicityRates.push(data[i]);
      }
    }
  }
}
