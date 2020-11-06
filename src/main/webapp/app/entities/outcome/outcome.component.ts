import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOutcome } from 'app/shared/model/outcome.model';

import { ITEMS_PER_PAGE } from 'app/core/config/pagination.constants';
import { OutcomeService } from './outcome.service';
import { OutcomeDeleteDialogComponent } from './outcome-delete-dialog.component';

@Component({
  selector: 'custom-outcome',
  templateUrl: './outcome.component.html',
})
export class OutcomeComponent implements OnInit, OnDestroy {
  outcomes: IOutcome[];
  eventSubscriber?: Subscription;
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected outcomeService: OutcomeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.outcomes = [];
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

    this.outcomeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IOutcome[]>) => {
          this.isLoading = false;
          this.paginateOutcomes(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.outcomes = [];
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
    this.registerChangeInOutcomes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOutcome): number {
    return item.id!;
  }

  registerChangeInOutcomes(): void {
    this.eventSubscriber = this.eventManager.subscribe('outcomeListModification', () => this.reset());
  }

  delete(outcome: IOutcome): void {
    const modalRef = this.modalService.open(OutcomeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.outcome = outcome;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateOutcomes(data: IOutcome[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.outcomes.push(data[i]);
      }
    }
  }
}
