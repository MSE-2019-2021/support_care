import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISymptom, Symptom } from '../symptom.model';
import { SymptomService } from '../service/symptom.service';
import { ITherapeuticRegime } from 'app/entities/therapeutic-regime/therapeutic-regime.model';
import { TherapeuticRegimeService } from 'app/entities/therapeutic-regime/service/therapeutic-regime.service';
import { IOutcome } from 'app/entities/outcome/outcome.model';
import { OutcomeService } from 'app/entities/outcome/service/outcome.service';
import { IToxicityRate } from 'app/entities/toxicity-rate/toxicity-rate.model';
import { ToxicityRateService } from 'app/entities/toxicity-rate/service/toxicity-rate.service';

@Component({
  selector: 'custom-symptom-update',
  templateUrl: './symptom-update.component.html',
})
export class SymptomUpdateComponent implements OnInit {
  isSaving = false;
  therapeuticregimes: ITherapeuticRegime[] = [];
  outcomes: IOutcome[] = [];
  toxicityrates: IToxicityRate[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    notice: [null, [Validators.maxLength(1000)]],
    therapeuticRegimes: [],
    outcomes: [],
    toxicityRates: [],
  });

  constructor(
    protected symptomService: SymptomService,
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected outcomeService: OutcomeService,
    protected toxicityRateService: ToxicityRateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptom }) => {
      this.updateForm(symptom);

      this.therapeuticRegimeService
        .query()
        .subscribe((res: HttpResponse<ITherapeuticRegime[]>) => (this.therapeuticregimes = res.body ?? []));

      this.outcomeService.query().subscribe((res: HttpResponse<IOutcome[]>) => (this.outcomes = res.body ?? []));

      this.toxicityRateService.query().subscribe((res: HttpResponse<IOutcome[]>) => (this.toxicityrates = res.body ?? []));
    });
  }

  updateForm(symptom: ISymptom): void {
    this.editForm.patchValue({
      id: symptom.id,
      name: symptom.name,
      notice: symptom.notice,
      therapeuticRegimes: symptom.therapeuticRegimes,
      outcomes: symptom.outcomes,
      toxicityRates: symptom.toxicityRates,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const symptom = this.createFromForm();
    if (symptom.id !== undefined) {
      this.subscribeToSaveResponse(this.symptomService.update(symptom));
    } else {
      this.subscribeToSaveResponse(this.symptomService.create(symptom));
    }
  }

  isEditing(): boolean {
    const symptom = this.createFromForm();
    return !!symptom.id;
  }

  private createFromForm(): ISymptom {
    return {
      ...new Symptom(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      notice: this.editForm.get(['notice'])!.value,
      therapeuticRegimes: this.editForm.get(['therapeuticRegimes'])!.value,
      outcomes: this.editForm.get(['outcomes'])!.value,
      toxicityRates: this.editForm.get(['toxicityRates'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISymptom>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackTherapeuticRegimeById(index: number, item: ITherapeuticRegime): number {
    return item.id!;
  }

  trackOutcomeById(index: number, item: IOutcome): number {
    return item.id!;
  }

  trackToxicityRateById(index: number, item: IToxicityRate): number {
    return item.id!;
  }

  getSelectedTherapeuticRegime(option: ITherapeuticRegime, selectedVals?: ITherapeuticRegime[]): ITherapeuticRegime {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  getSelectedOutcome(option: IOutcome, selectedVals?: IOutcome[]): IOutcome {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  getSelectedToxicityRate(option: IToxicityRate, selectedVals?: IToxicityRate[]): IToxicityRate {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
