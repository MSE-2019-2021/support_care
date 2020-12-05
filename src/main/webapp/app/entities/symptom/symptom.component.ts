import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISymptom } from 'app/shared/model/symptom.model';

import { ITEMS_PER_PAGE } from 'app/core/config/pagination.constants';
import { SymptomService } from './symptom.service';
import { SymptomDeleteDialogComponent } from './symptom-delete-dialog.component';

// import { Pipe, PipeTransform } from '@angular/core';
//
// @Pipe({
//   name: 'filter'
// })

@Component({
  selector: 'custom-symptom',
  templateUrl: './symptom.component.html',
})
export class SymptomComponent implements OnInit, OnDestroy/*, PipeTransform */  {
  symptoms: ISymptom[];
  eventSubscriber?: Subscription;
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected symptomService: SymptomService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.symptoms = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'name';
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

  handleSyncList(): void {
    this.reset();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSymptoms();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISymptom): number {
    return item.id!;
  }

  registerChangeInSymptoms(): void {
    this.eventSubscriber = this.eventManager.subscribe('symptomListModification', () => this.reset());
  }

  delete(symptom: ISymptom): void {
    const modalRef = this.modalService.open(SymptomDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.symptom = symptom;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSymptoms(data: ISymptom[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.symptoms.push(data[i]);
      }
    }
  }
  // transform(items: any[], searchText: string): any[] {
  //
  //   if (!items) {
  //     return [];
  //   }
  //   if (!searchText) {
  //     return items;
  //   }
  //   searchText = searchText.toLocaleLowerCase();
  //
  //   return items.filter(it => {
  //     return it.toLocaleLowerCase().includes(searchText);
  //   });
  // }
}
