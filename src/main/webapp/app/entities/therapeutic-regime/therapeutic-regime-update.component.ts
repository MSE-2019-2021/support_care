import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITherapeuticRegime, TherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from './therapeutic-regime.service';
import { IDrug } from 'app/shared/model/drug.model';
import { DrugService } from 'app/entities/drug/drug.service';
import { ITreatment } from 'app/shared/model/treatment.model';
import { TreatmentService } from 'app/entities/treatment/treatment.service';

type SelectableEntity = IDrug | ITreatment;

@Component({
  selector: 'jhi-therapeutic-regime-update',
  templateUrl: './therapeutic-regime-update.component.html',
})
export class TherapeuticRegimeUpdateComponent implements OnInit {
  isSaving = false;
  drugs: IDrug[] = [];
  treatments: ITreatment[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    acronym: [],
    purpose: [null, [Validators.required]],
    condition: [null, [Validators.required]],
    timing: [],
    indication: [null, [Validators.required]],
    criteria: [null, [Validators.required]],
    notice: [],
    drugs: [],
    treatmentId: [null, Validators.required],
  });

  constructor(
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected drugService: DrugService,
    protected treatmentService: TreatmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.updateForm(therapeuticRegime);

      this.drugService.query().subscribe((res: HttpResponse<IDrug[]>) => (this.drugs = res.body || []));

      this.treatmentService.query().subscribe((res: HttpResponse<ITreatment[]>) => (this.treatments = res.body || []));
    });
  }

  updateForm(therapeuticRegime: ITherapeuticRegime): void {
    this.editForm.patchValue({
      id: therapeuticRegime.id,
      name: therapeuticRegime.name,
      acronym: therapeuticRegime.acronym,
      purpose: therapeuticRegime.purpose,
      condition: therapeuticRegime.condition,
      timing: therapeuticRegime.timing,
      indication: therapeuticRegime.indication,
      criteria: therapeuticRegime.criteria,
      notice: therapeuticRegime.notice,
      drugs: therapeuticRegime.drugs,
      treatmentId: therapeuticRegime.treatmentId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const therapeuticRegime = this.createFromForm();
    if (therapeuticRegime.id !== undefined) {
      this.subscribeToSaveResponse(this.therapeuticRegimeService.update(therapeuticRegime));
    } else {
      this.subscribeToSaveResponse(this.therapeuticRegimeService.create(therapeuticRegime));
    }
  }

  private createFromForm(): ITherapeuticRegime {
    return {
      ...new TherapeuticRegime(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      acronym: this.editForm.get(['acronym'])!.value,
      purpose: this.editForm.get(['purpose'])!.value,
      condition: this.editForm.get(['condition'])!.value,
      timing: this.editForm.get(['timing'])!.value,
      indication: this.editForm.get(['indication'])!.value,
      criteria: this.editForm.get(['criteria'])!.value,
      notice: this.editForm.get(['notice'])!.value,
      drugs: this.editForm.get(['drugs'])!.value,
      treatmentId: this.editForm.get(['treatmentId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITherapeuticRegime>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IDrug[], option: IDrug): IDrug {
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
