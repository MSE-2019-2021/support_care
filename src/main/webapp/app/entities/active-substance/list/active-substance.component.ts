import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActiveSubstance } from '../active-substance.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ActiveSubstanceService } from '../service/active-substance.service';
import { ActiveSubstanceDeleteDialogComponent } from '../delete/active-substance-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'custom-active-substance',
  templateUrl: './active-substance.component.html',
})
export class ActiveSubstanceComponent implements OnInit {
  activeSubstances: IActiveSubstance[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected activeSubstanceService: ActiveSubstanceService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.activeSubstances = [];
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

    this.activeSubstanceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IActiveSubstance[]>) => {
          this.isLoading = false;
          this.paginateActiveSubstances(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.activeSubstances = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IActiveSubstance): number {
    return item.id!;
  }

  delete(activeSubstance: IActiveSubstance): void {
    const modalRef = this.modalService.open(ActiveSubstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.activeSubstance = activeSubstance;
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

  protected paginateActiveSubstances(data: IActiveSubstance[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.activeSubstances.push(data[i]);
      }
    }
  }
}
