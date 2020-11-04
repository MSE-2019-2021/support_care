import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdministration } from 'app/shared/model/administration.model';

import { ITEMS_PER_PAGE } from 'app/core/config/pagination.constants';
import { AdministrationService } from './administration.service';
import { AdministrationDeleteDialogComponent } from './administration-delete-dialog.component';

@Component({
  selector: 'jhi-administration',
  templateUrl: './administration.component.html',
})
export class AdministrationComponent implements OnInit, OnDestroy {
  administrations: IAdministration[];
  eventSubscriber?: Subscription;
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected administrationService: AdministrationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.administrations = [];
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

    this.administrationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IAdministration[]>) => {
          this.isLoading = false;
          this.paginateAdministrations(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.administrations = [];
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
    this.registerChangeInAdministrations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAdministration): number {
    return item.id!;
  }

  registerChangeInAdministrations(): void {
    this.eventSubscriber = this.eventManager.subscribe('administrationListModification', () => this.reset());
  }

  delete(administration: IAdministration): void {
    const modalRef = this.modalService.open(AdministrationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.administration = administration;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAdministrations(data: IAdministration[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.administrations.push(data[i]);
      }
    }
  }
}
