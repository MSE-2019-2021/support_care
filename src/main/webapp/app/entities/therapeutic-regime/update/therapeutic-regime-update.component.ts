import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITherapeuticRegime, TherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from '../therapeutic-regime.service';
import { IDrug } from 'app/shared/model/drug.model';
import { DrugService } from 'app/entities/drug/drug.service';
import { ITreatment } from 'app/shared/model/treatment.model';
import { TreatmentService } from 'app/entities/treatment/treatment.service';

import { TherapeuticRegimeCancelDialogComponent } from 'app/entities/therapeutic-regime/cancel/therapeutic-regime-cancel-dialog.component';
import { Router } from '@angular/router';

type SelectableEntity = IDrug | ITreatment;

@Component({
  selector: 'custom-therapeutic-regime-update',
  templateUrl: './therapeutic-regime-update.component.html',
})
export class TherapeuticRegimeUpdateComponent implements OnInit {
  isSaving = false;
  drugs: IDrug[] = [];
  treatments: ITreatment[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(250)]],
    acronym: [null, [Validators.maxLength(50)]],
    purpose: [null, [Validators.required, Validators.maxLength(1000)]],
    condition: [null, [Validators.required, Validators.maxLength(1000)]],
    timing: [null, [Validators.maxLength(250)]],
    indication: [null, [Validators.required, Validators.maxLength(1000)]],
    criteria: [null, [Validators.required, Validators.maxLength(1000)]],
    notice: [null, [Validators.maxLength(1000)]],
    drugs: [null, [Validators.required]],
    treatment: [null, Validators.required],
  });

  dropdownList: { id: number; text: string }[] = [];
  dropdownSettings = {};

  constructor(
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected drugService: DrugService,
    protected treatmentService: TreatmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private route: Router,
    protected modalService: NgbModal,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true,
    };
    if (this.activeModal) {
      return;
    }
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.updateForm(therapeuticRegime);

      this.drugService.query().subscribe((res: HttpResponse<IDrug[]>) => (this.drugs = res.body ?? []));

      this.treatmentService.query().subscribe((res: HttpResponse<ITreatment[]>) => (this.treatments = res.body ?? []));
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
      drugs: this.getSelect2OptionsDrugs(therapeuticRegime.drugs!),
      treatment: therapeuticRegime.treatment,
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
      drugs: this.editForm.get(['drugs'])!.value,
      treatment: this.editForm.get(['treatment'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITherapeuticRegime>>): void {
    result.subscribe(
      r => this.onSaveSuccess(r.body!.id),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(id: number | undefined): void {
    this.isSaving = false;
    this.route.navigate(['/therapeutic-regime', id, 'view']);
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): number {
    return item.id!;
  }

  cancel(): void {
    const modalRef = this.modalService.open(TherapeuticRegimeCancelDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'canceled') {
        this.previousState();
      }
    });
  }

  getSelect2OptionsDrugs(options: IDrug[]): { id: number; text: string }[] {
    const dropdownList: { id: number; text: string }[] = [];
    if (typeof options === 'undefined' || options.length === 0) {
      return [];
    }
    options.forEach(value => {
      dropdownList.push({
        id: value.id!,
        text: value.name!,
      });
    });
    return dropdownList;
  }
}
