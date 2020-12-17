import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISymptom } from '../symptom.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { SymptomService } from '../service/symptom.service';
import { SymptomDeleteDialogComponent } from '../delete/symptom-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'custom-symptom',
  templateUrl: './symptom.component.html',
})
export class SymptomComponent implements OnInit {
  symptoms: ISymptom[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected symptomService: SymptomService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.symptoms = [];
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

    this.symptomService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<ISymptom[]>) => {
          this.isLoading = false;
          this.paginateSymptoms(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.symptoms = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ISymptom): number {
    return item.id!;
  }

  delete(symptom: ISymptom): void {
    const modalRef = this.modalService.open(SymptomDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.symptom = symptom;
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

  protected paginateSymptoms(data: ISymptom[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.symptoms.push(data[i]);
      }
    }
  }
}
