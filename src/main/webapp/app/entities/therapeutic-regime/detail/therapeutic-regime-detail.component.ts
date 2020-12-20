import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITherapeuticRegime } from '../therapeutic-regime.model';

@Component({
  selector: 'custom-therapeutic-regime-detail',
  templateUrl: './therapeutic-regime-detail.component.html',
})
export class TherapeuticRegimeDetailComponent implements OnInit {
  therapeuticRegime: ITherapeuticRegime | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.therapeuticRegime = therapeuticRegime;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
