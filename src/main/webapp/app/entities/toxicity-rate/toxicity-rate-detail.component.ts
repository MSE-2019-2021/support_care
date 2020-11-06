import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IToxicityRate } from 'app/shared/model/toxicity-rate.model';

@Component({
  selector: 'custom-toxicity-rate-detail',
  templateUrl: './toxicity-rate-detail.component.html',
})
export class ToxicityRateDetailComponent implements OnInit {
  toxicityRate: IToxicityRate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ toxicityRate }) => {
      this.toxicityRate = toxicityRate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
