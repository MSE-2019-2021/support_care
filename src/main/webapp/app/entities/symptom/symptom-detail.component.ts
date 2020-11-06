import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISymptom } from 'app/shared/model/symptom.model';

@Component({
  selector: 'custom-symptom-detail',
  templateUrl: './symptom-detail.component.html',
})
export class SymptomDetailComponent implements OnInit {
  symptom: ISymptom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptom }) => {
      this.symptom = symptom;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
