import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDrug } from 'app/shared/model/drug.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DrugService } from './drug.service';
import { DrugDeleteDialogComponent } from './drug-delete-dialog.component';

@Component({
  selector: 'jhi-drug',
  templateUrl: './drug.component.html',
})
export class DrugComponent implements OnInit, OnDestroy {
  drugs: IDrug[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected drugService: DrugService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.drugs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.drugService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IDrug[]>) => this.paginateDrugs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.drugs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDrugs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDrug): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDrugs(): void {
    this.eventSubscriber = this.eventManager.subscribe('drugListModification', () => this.reset());
  }

  delete(drug: IDrug): void {
    const modalRef = this.modalService.open(DrugDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.drug = drug;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDrugs(data: IDrug[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.drugs.push(data[i]);
      }
    }
  }
}
