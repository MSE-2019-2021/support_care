import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDrug, Drug } from 'app/shared/model/drug.model';
import { DrugService } from './drug.service';
import { IAdministration } from 'app/shared/model/administration.model';
import { AdministrationService } from 'app/entities/administration/administration.service';
import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from 'app/entities/therapeutic-regime/therapeutic-regime.service';

type SelectableEntity = IAdministration | ITherapeuticRegime;

@Component({
  selector: 'jhi-drug-update',
  templateUrl: './drug-update.component.html',
})
export class DrugUpdateComponent implements OnInit {
  isSaving = false;
  administrations: IAdministration[] = [];
  therapeuticregimes: ITherapeuticRegime[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    administrationId: [null, Validators.required],
    therapeuticRegimeId: [],
  });

  constructor(
    protected drugService: DrugService,
    protected administrationService: AdministrationService,
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ drug }) => {
      this.updateForm(drug);

      this.administrationService
        .query({ 'drugId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAdministration[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAdministration[]) => {
          if (!drug.administrationId) {
            this.administrations = resBody;
          } else {
            this.administrationService
              .find(drug.administrationId)
              .pipe(
                map((subRes: HttpResponse<IAdministration>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAdministration[]) => (this.administrations = concatRes));
          }
        });

      this.therapeuticRegimeService
        .query()
        .subscribe((res: HttpResponse<ITherapeuticRegime[]>) => (this.therapeuticregimes = res.body || []));
    });
  }

  updateForm(drug: IDrug): void {
    this.editForm.patchValue({
      id: drug.id,
      name: drug.name,
      description: drug.description,
      administrationId: drug.administrationId,
      therapeuticRegimeId: drug.therapeuticRegimeId,
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
      therapeuticRegimeId: this.editForm.get(['therapeuticRegimeId'])!.value,
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
