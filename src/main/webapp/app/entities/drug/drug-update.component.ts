import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDrug, Drug } from 'app/shared/model/drug.model';
import { DrugService } from './drug.service';

@Component({
  selector: 'jhi-drug-update',
  templateUrl: './drug-update.component.html',
})
export class DrugUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    drugName: [null, [Validators.required]],
    createUser: [null, [Validators.required]],
    createDate: [null, [Validators.required]],
    updateUser: [null, [Validators.required]],
    updateDate: [null, [Validators.required]],
  });

  constructor(protected drugService: DrugService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ drug }) => {
      if (!drug.id) {
        const today = moment().startOf('day');
        drug.createDate = today;
        drug.updateDate = today;
      }

      this.updateForm(drug);
    });
  }

  updateForm(drug: IDrug): void {
    this.editForm.patchValue({
      id: drug.id,
      drugName: drug.drugName,
      createUser: drug.createUser,
      createDate: drug.createDate ? drug.createDate.format(DATE_TIME_FORMAT) : null,
      updateUser: drug.updateUser,
      updateDate: drug.updateDate ? drug.updateDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const drug = this.createFromForm();
    if (drug.id !== undefined) {
      this.subscribeToSaveResponse(this.drugService.update(drug));
    } else {
      this.subscribeToSaveResponse(this.drugService.create(drug));
    }
  }

  private createFromForm(): IDrug {
    return {
      ...new Drug(),
      id: this.editForm.get(['id'])!.value,
      drugName: this.editForm.get(['drugName'])!.value,
      createUser: this.editForm.get(['createUser'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      updateUser: this.editForm.get(['updateUser'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value ? moment(this.editForm.get(['updateDate'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDrug>>): void {
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
