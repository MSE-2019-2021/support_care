import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDrug } from 'app/shared/model/drug.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { DrugService } from '../drug.service';
import { DrugDeleteDialogComponent } from '../delete/drug-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'custom-drug',
  templateUrl: './drug.component.html',
})
export class DrugComponent implements OnInit {
  drugs: IDrug[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected drugService: DrugService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
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
    this.isLoading = true;

    this.drugService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IDrug[]>) => {
          this.isLoading = false;
          this.paginateDrugs(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
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
  }

  trackId(index: number, item: IDrug): number {
    return item.id!;
  }

  delete(drug: IDrug): void {
    const modalRef = this.modalService.open(DrugDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.drug = drug;
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

  protected paginateDrugs(data: IDrug[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.drugs.push(data[i]);
      }
    }
  }
}
