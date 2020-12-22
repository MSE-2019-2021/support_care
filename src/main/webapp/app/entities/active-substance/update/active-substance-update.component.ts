import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActiveSubstance, ActiveSubstance } from '../active-substance.model';
import { ActiveSubstanceService } from '../service/active-substance.service';
import { INotice } from 'app/entities/notice/notice.model';
import { NoticeService } from 'app/entities/notice/service/notice.service';
import { IAdministration } from 'app/entities/administration/administration.model';
import { AdministrationService } from 'app/entities/administration/service/administration.service';

@Component({
  selector: 'custom-active-substance-update',
  templateUrl: './active-substance-update.component.html',
})
export class ActiveSubstanceUpdateComponent implements OnInit {
  isSaving = false;
  notices: INotice[] = [];
  administrations: IAdministration[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    dosage: [null, [Validators.required, Validators.maxLength(30)]],
    form: [null, [Validators.required, Validators.maxLength(255)]],
    description: [null, [Validators.maxLength(1000)]],
    notices: [],
    administration: [],
  });

  constructor(
    protected activeSubstanceService: ActiveSubstanceService,
    protected noticeService: NoticeService,
    protected administrationService: AdministrationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activeSubstance }) => {
      this.updateForm(activeSubstance);

      this.noticeService.query().subscribe((res: HttpResponse<INotice[]>) => (this.notices = res.body ?? []));

      this.administrationService.query().subscribe((res: HttpResponse<IAdministration[]>) => (this.administrations = res.body ?? []));
    });
  }

  updateForm(activeSubstance: IActiveSubstance): void {
    this.editForm.patchValue({
      id: activeSubstance.id,
      name: activeSubstance.name,
      dosage: activeSubstance.dosage,
      form: activeSubstance.form,
      description: activeSubstance.description,
      administration: activeSubstance.administration,
      notices: activeSubstance.notices,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activeSubstance = this.createFromForm();
    if (activeSubstance.id !== undefined) {
      this.subscribeToSaveResponse(this.activeSubstanceService.update(activeSubstance));
    } else {
      this.subscribeToSaveResponse(this.activeSubstanceService.create(activeSubstance));
    }
  }

  isEditing(): boolean {
    const activeSubstance = this.createFromForm();
    return !!activeSubstance.id;
  }

  private createFromForm(): IActiveSubstance {
    return {
      ...new ActiveSubstance(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      dosage: this.editForm.get(['dosage'])!.value,
      form: this.editForm.get(['form'])!.value,
      description: this.editForm.get(['description'])!.value,
      administration: this.editForm.get(['administration'])!.value,
      notices: this.editForm.get(['notices'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActiveSubstance>>): void {
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

  trackNoticeById(index: number, item: INotice): number {
    return item.id!;
  }

  trackAdministrationById(index: number, item: IAdministration): number {
    return item.id!;
  }

  getSelectedNotice(option: INotice, selectedVals?: INotice[]): INotice {
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
