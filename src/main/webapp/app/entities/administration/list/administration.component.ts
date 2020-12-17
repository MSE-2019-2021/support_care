import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdministration } from '../administration.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { AdministrationService } from '../service/administration.service';
import { AdministrationDeleteDialogComponent } from '../delete/administration-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'custom-administration',
  templateUrl: './administration.component.html',
})
export class AdministrationComponent implements OnInit {
  administrations: IAdministration[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected administrationService: AdministrationService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
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

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAdministration): number {
    return item.id!;
  }

  delete(administration: IAdministration): void {
    const modalRef = this.modalService.open(AdministrationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.administration = administration;
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

  protected paginateAdministrations(data: IAdministration[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.administrations.push(data[i]);
      }
    }
  }
}
