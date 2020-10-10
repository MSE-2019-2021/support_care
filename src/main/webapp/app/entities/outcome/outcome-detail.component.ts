import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOutcome } from 'app/shared/model/outcome.model';

@Component({
  selector: 'jhi-outcome-detail',
  templateUrl: './outcome-detail.component.html',
})
export class OutcomeDetailComponent implements OnInit {
  outcome: IOutcome | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcome }) => (this.outcome = outcome));
  }

  previousState(): void {
    window.history.back();
  }
}
