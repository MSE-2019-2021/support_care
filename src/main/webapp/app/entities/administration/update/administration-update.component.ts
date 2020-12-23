import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdministration, Administration } from '../administration.model';
import { AdministrationService } from '../service/administration.service';

@Component({
  selector: 'custom-administration-update',
  templateUrl: './administration-update.component.html',
})
export class AdministrationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required, Validators.maxLength(100)]],
  });

  constructor(protected administrationService: AdministrationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administration }) => {
      this.updateForm(administration);
    });
  }

  updateForm(administration: IAdministration): void {
    this.editForm.patchValue({
      id: administration.id,
      type: administration.type,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const administration = this.createFromForm();
    if (administration.id !== undefined) {
      this.subscribeToSaveResponse(this.administrationService.update(administration));
    } else {
      this.subscribeToSaveResponse(this.administrationService.create(administration));
    }
  }

  private createFromForm(): IAdministration {
    return {
      ...new Administration(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdministration>>): void {
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
