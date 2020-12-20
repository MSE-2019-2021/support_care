import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActiveSubstance } from '../active-substance.model';

@Component({
  selector: 'custom-active-substance-detail',
  templateUrl: './active-substance-detail.component.html',
})
export class ActiveSubstanceDetailComponent implements OnInit {
  activeSubstance: IActiveSubstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activeSubstance }) => {
      this.activeSubstance = activeSubstance;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
