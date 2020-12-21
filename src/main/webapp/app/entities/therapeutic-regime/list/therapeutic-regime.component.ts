import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ITherapeuticRegime } from '../therapeutic-regime.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { TherapeuticRegimeService } from '../service/therapeutic-regime.service';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'custom-therapeutic-regime',
  templateUrl: './therapeutic-regime.component.html',
})
export class TherapeuticRegimeComponent implements OnInit {
  therapeuticRegimes: ITherapeuticRegime[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected therapeuticRegimeService: TherapeuticRegimeService, protected parseLinks: ParseLinks) {
    this.therapeuticRegimes = [];
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

    this.therapeuticRegimeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<ITherapeuticRegime[]>) => {
          this.isLoading = false;
          this.paginateTherapeuticRegimes(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
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
  }

  trackId(index: number, item: ITherapeuticRegime): number {
    return item.id!;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTherapeuticRegimes(data: ITherapeuticRegime[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.therapeuticRegimes.push(data[i]);
      }
    }
  }
}
