import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IToxicityRate, ToxicityRate } from 'app/shared/model/toxicity-rate.model';
import { ToxicityRateService } from './toxicity-rate.service';

@Component({
  selector: 'custom-toxicity-rate-update',
  templateUrl: './toxicity-rate-update.component.html',
})
export class ToxicityRateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    notice: [],
    autonomousIntervention: [],
    interdependentIntervention: [],
    selfManagement: [],
  });

  constructor(
    protected toxicityRateService: ToxicityRateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.activeModal) {
      return;
    }
    this.activatedRoute.data.subscribe(({ toxicityRate }) => {
      this.updateForm(toxicityRate);
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
}
