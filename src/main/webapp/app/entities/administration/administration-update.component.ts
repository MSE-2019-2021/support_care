import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdministration, Administration } from 'app/shared/model/administration.model';
import { AdministrationService } from './administration.service';

@Component({
  selector: 'jhi-administration-update',
  templateUrl: './administration-update.component.html',
})
export class AdministrationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
  });

  constructor(
    protected administrationService: AdministrationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.activeModal) {
      return;
    }
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
    if (this.activeModal) {
      this.activeModal.close();
    } else {
      window.history.back();
    }
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
