import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDrug, Drug } from 'app/shared/model/drug.model';
import { DrugService } from './drug.service';
import { IAdministration } from 'app/shared/model/administration.model';
import { AdministrationService } from 'app/entities/administration/administration.service';

@Component({
  selector: 'jhi-drug-update',
  templateUrl: './drug-update.component.html',
})
export class DrugUpdateComponent implements OnInit {
  isSaving = false;
  administrations: IAdministration[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    administrationId: [null, Validators.required],
  });

  constructor(
    protected drugService: DrugService,
    protected administrationService: AdministrationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ drug }) => {
      this.updateForm(drug);

      this.administrationService.query().subscribe((res: HttpResponse<IAdministration[]>) => (this.administrations = res.body || []));
    });
  }

  updateForm(drug: IDrug): void {
    this.editForm.patchValue({
      id: drug.id,
      name: drug.name,
      description: drug.description,
      administrationId: drug.administrationId,
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
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      administrationId: this.editForm.get(['administrationId'])!.value,
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

  trackById(index: number, item: IAdministration): any {
    return item.id;
  }
}
