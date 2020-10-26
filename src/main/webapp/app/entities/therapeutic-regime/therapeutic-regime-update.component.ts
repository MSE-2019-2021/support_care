import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITherapeuticRegime, TherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from './therapeutic-regime.service';
import { ITreatment } from 'app/shared/model/treatment.model';
import { TreatmentService } from 'app/entities/treatment/treatment.service';
import { IDiagnostic } from 'app/shared/model/diagnostic.model';
import { DiagnosticService } from 'app/entities/diagnostic/diagnostic.service';

type SelectableEntity = ITreatment | IDiagnostic;

@Component({
  selector: 'jhi-therapeutic-regime-update',
  templateUrl: './therapeutic-regime-update.component.html',
})
export class TherapeuticRegimeUpdateComponent implements OnInit {
  isSaving = false;
  treatments: ITreatment[] = [];
  diagnostics: IDiagnostic[] = [];

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
    treatmentId: [null, Validators.required],
    diagnosticId: [],
  });

  constructor(
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected treatmentService: TreatmentService,
    protected diagnosticService: DiagnosticService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.updateForm(therapeuticRegime);

      this.treatmentService
        .query({ 'therapeuticRegimeId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ITreatment[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITreatment[]) => {
          if (!therapeuticRegime.treatmentId) {
            this.treatments = resBody;
          } else {
            this.treatmentService
              .find(therapeuticRegime.treatmentId)
              .pipe(
                map((subRes: HttpResponse<ITreatment>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITreatment[]) => (this.treatments = concatRes));
          }
        });

      this.diagnosticService.query().subscribe((res: HttpResponse<IDiagnostic[]>) => (this.diagnostics = res.body || []));
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
      treatmentId: therapeuticRegime.treatmentId,
      diagnosticId: therapeuticRegime.diagnosticId,
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
      treatmentId: this.editForm.get(['treatmentId'])!.value,
      diagnosticId: this.editForm.get(['diagnosticId'])!.value,
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
}
