import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDrug, Drug } from 'app/shared/model/drug.model';
import { DrugService } from '../drug.service';
import { INotice } from 'app/shared/model/notice.model';
import { NoticeService } from 'app/entities/notice/notice.service';
import { IAdministration } from 'app/shared/model/administration.model';
import { AdministrationService } from 'app/entities/administration/administration.service';

type SelectableEntity = INotice | IAdministration;

@Component({
  selector: 'custom-drug-update',
  templateUrl: './drug-update.component.html',
})
export class DrugUpdateComponent implements OnInit {
  isSaving = false;
  notices: INotice[] = [];
  administrations: IAdministration[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(250)]],
    description: [null, [Validators.maxLength(1000)]],
    notices: [],
    administration: [null, Validators.required],
  });

  constructor(
    protected drugService: DrugService,
    protected noticeService: NoticeService,
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

      this.noticeService.query().subscribe((res: HttpResponse<INotice[]>) => (this.notices = res.body ?? []));

      this.administrationService.query().subscribe((res: HttpResponse<IAdministration[]>) => (this.administrations = res.body ?? []));
    });
  }

  updateForm(drug: IDrug): void {
    this.editForm.patchValue({
      id: drug.id,
      name: drug.name,
      description: drug.description,
      notices: drug.notices,
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
      notices: this.editForm.get(['notices'])!.value,
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

  trackById(index: number, item: SelectableEntity): number {
    return item.id!;
  }

  getSelected(option: INotice, selectedVals?: INotice[]): INotice {
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
