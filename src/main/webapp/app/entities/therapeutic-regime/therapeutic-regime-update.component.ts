import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITherapeuticRegime, TherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from './therapeutic-regime.service';
import { IDrug } from 'app/shared/model/drug.model';
import { DrugService } from 'app/entities/drug/drug.service';

@Component({
  selector: 'jhi-therapeutic-regime-update',
  templateUrl: './therapeutic-regime-update.component.html',
})
export class TherapeuticRegimeUpdateComponent implements OnInit {
  isSaving = false;
  drugs: IDrug[] = [];

  editForm = this.fb.group({
    id: [],
    timing: [null, [Validators.required]],
    dietary: [null, [Validators.required]],
    sideEffects: [null, [Validators.required]],
    createUser: [null, [Validators.required]],
    createDate: [null, [Validators.required]],
    updateUser: [null, [Validators.required]],
    updateDate: [null, [Validators.required]],
    drugId: [],
  });

  constructor(
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected drugService: DrugService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      if (!therapeuticRegime.id) {
        const today = moment().startOf('day');
        therapeuticRegime.createDate = today;
        therapeuticRegime.updateDate = today;
      }

      this.updateForm(therapeuticRegime);

      this.drugService.query().subscribe((res: HttpResponse<IDrug[]>) => (this.drugs = res.body || []));
    });
  }

  updateForm(therapeuticRegime: ITherapeuticRegime): void {
    this.editForm.patchValue({
      id: therapeuticRegime.id,
      timing: therapeuticRegime.timing,
      dietary: therapeuticRegime.dietary,
      sideEffects: therapeuticRegime.sideEffects,
      createUser: therapeuticRegime.createUser,
      createDate: therapeuticRegime.createDate ? therapeuticRegime.createDate.format(DATE_TIME_FORMAT) : null,
      updateUser: therapeuticRegime.updateUser,
      updateDate: therapeuticRegime.updateDate ? therapeuticRegime.updateDate.format(DATE_TIME_FORMAT) : null,
      drugId: therapeuticRegime.drugId,
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
      timing: this.editForm.get(['timing'])!.value,
      dietary: this.editForm.get(['dietary'])!.value,
      sideEffects: this.editForm.get(['sideEffects'])!.value,
      createUser: this.editForm.get(['createUser'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      updateUser: this.editForm.get(['updateUser'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value ? moment(this.editForm.get(['updateDate'])!.value, DATE_TIME_FORMAT) : undefined,
      drugId: this.editForm.get(['drugId'])!.value,
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

  trackById(index: number, item: IDrug): any {
    return item.id;
  }
}
