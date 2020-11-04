import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITreatment, Treatment } from 'app/shared/model/treatment.model';
import { TreatmentService } from './treatment.service';

@Component({
  selector: 'jhi-treatment-update',
  templateUrl: './treatment-update.component.html',
})
export class TreatmentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
  });

  constructor(
    protected treatmentService: TreatmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.activeModal) {
      return;
    }
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
    if (this.activeModal) {
      this.activeModal.close();
    } else {
      window.history.back();
    }
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
