import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOutcome } from '../outcome.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { OutcomeService } from '../service/outcome.service';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'custom-outcome',
  templateUrl: './outcome.component.html',
})
export class OutcomeComponent implements OnInit {
  outcomes: IOutcome[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;
  searchName: string | undefined;
  timer: ReturnType<typeof setTimeout> = setTimeout(() => '', 200);

  constructor(protected outcomeService: OutcomeService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.outcomes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'name';
    this.ascending = true;
  }

  getCriteria(): {} {
    let criteria = {};

    if (this.searchName) {
      criteria = { 'name.contains': this.searchName };
    }
    return criteria;
  }

  loadAll(): void {
    this.isLoading = true;

    this.outcomeService
      .query(Object.assign(
        {},
        {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        this.getCriteria()
      ))
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

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IOutcome): number {
    return item.id!;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateOutcomes(data: IOutcome[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.outcomes.push(data[i]);
      }
    }
  }

  searching(): any {
    clearTimeout(this.timer);

    this.timer = setTimeout(() => {
      this.reset();
    }, 200);
  }
}
