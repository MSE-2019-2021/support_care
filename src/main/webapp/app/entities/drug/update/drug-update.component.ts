import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDrug, Drug } from '../drug.model';
import { DrugService } from '../service/drug.service';
import { IAdministration } from 'app/entities/administration/administration.model';
import { AdministrationService } from 'app/entities/administration/service/administration.service';

@Component({
  selector: 'custom-drug-update',
  templateUrl: './drug-update.component.html',
})
export class DrugUpdateComponent implements OnInit {
  isSaving = false;
  administrations: IAdministration[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(250)]],
    description: [null, [Validators.maxLength(1000)]],
    administration: [null, Validators.required],
  });

  constructor(
    protected drugService: DrugService,
    protected administrationService: AdministrationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.activeModal) {
      return;
    }
    this.activatedRoute.data.subscribe(({ drug }) => {
      this.updateForm(drug);

      this.administrationService.query().subscribe((res: HttpResponse<IAdministration[]>) => (this.administrations = res.body ?? []));
    });
  }

  updateForm(drug: IDrug): void {
    this.editForm.patchValue({
      id: drug.id,
      name: drug.name,
      description: drug.description,
      administration: drug.administration,
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
      administration: this.editForm.get(['administration'])!.value,
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

  trackAdministrationById(index: number, item: IAdministration): number {
    return item.id!;
  }
}
