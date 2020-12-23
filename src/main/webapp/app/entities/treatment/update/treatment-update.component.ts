import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITreatment, Treatment } from '../treatment.model';
import { TreatmentService } from '../service/treatment.service';

@Component({
  selector: 'custom-treatment-update',
  templateUrl: './treatment-update.component.html',
})
export class TreatmentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required, Validators.maxLength(100)]],
  });

  constructor(protected treatmentService: TreatmentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ treatment }) => {
      this.updateForm(treatment);
    });
  }

  updateForm(treatment: ITreatment): void {
    this.editForm.patchValue({
      id: treatment.id,
      type: treatment.type,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const treatment = this.createFromForm();
    if (treatment.id !== undefined) {
      this.subscribeToSaveResponse(this.treatmentService.update(treatment));
    } else {
      this.subscribeToSaveResponse(this.treatmentService.create(treatment));
    }
  }

  private createFromForm(): ITreatment {
    return {
      ...new Treatment(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITreatment>>): void {
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
}
