import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IToxicityRate, ToxicityRate } from '../toxicity-rate.model';
import { ToxicityRateService } from '../service/toxicity-rate.service';
import { ISymptom } from 'app/entities/symptom/symptom.model';
import { SymptomService } from 'app/entities/symptom/service/symptom.service';

@Component({
  selector: 'custom-toxicity-rate-update',
  templateUrl: './toxicity-rate-update.component.html',
})
export class ToxicityRateUpdateComponent implements OnInit {
  isSaving = false;
  symptoms: ISymptom[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    description: [null, [Validators.maxLength(1000)]],
    notice: [null, [Validators.maxLength(1000)]],
    autonomousIntervention: [null, [Validators.maxLength(1000)]],
    interdependentIntervention: [null, [Validators.maxLength(1000)]],
    selfManagement: [null, [Validators.maxLength(1000)]],
    symptom: [],
  });

  constructor(
    protected toxicityRateService: ToxicityRateService,
    protected symptomService: SymptomService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ toxicityRate }) => {
      this.updateForm(toxicityRate);

      this.symptomService.query().subscribe((res: HttpResponse<ISymptom[]>) => (this.symptoms = res.body ?? []));
    });
  }

  updateForm(toxicityRate: IToxicityRate): void {
    this.editForm.patchValue({
      id: toxicityRate.id,
      name: toxicityRate.name,
      description: toxicityRate.description,
      notice: toxicityRate.notice,
      autonomousIntervention: toxicityRate.autonomousIntervention,
      interdependentIntervention: toxicityRate.interdependentIntervention,
      selfManagement: toxicityRate.selfManagement,
      symptom: toxicityRate.symptom,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const toxicityRate = this.createFromForm();
    if (toxicityRate.id !== undefined) {
      this.subscribeToSaveResponse(this.toxicityRateService.update(toxicityRate));
    } else {
      this.subscribeToSaveResponse(this.toxicityRateService.create(toxicityRate));
    }
  }

  private createFromForm(): IToxicityRate {
    return {
      ...new ToxicityRate(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      notice: this.editForm.get(['notice'])!.value,
      autonomousIntervention: this.editForm.get(['autonomousIntervention'])!.value,
      interdependentIntervention: this.editForm.get(['interdependentIntervention'])!.value,
      selfManagement: this.editForm.get(['selfManagement'])!.value,
      symptom: this.editForm.get(['symptom'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IToxicityRate>>): void {
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

  trackSymptomById(index: number, item: ISymptom): number {
    return item.id!;
  }
}
