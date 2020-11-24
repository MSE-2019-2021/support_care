import { Component, OnInit, Optional, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITherapeuticRegime, TherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from './therapeutic-regime.service';
import { IDrug } from 'app/shared/model/drug.model';
import { DrugService } from 'app/entities/drug/drug.service';
import { ITreatment } from 'app/shared/model/treatment.model';
import { TreatmentService } from 'app/entities/treatment/treatment.service';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TherapeuticRegimeCancelDialogComponent } from './therapeutic-regime-cancel-dialog.component';
import { Select2Option } from 'ng-select2-component';

type SelectableEntity = IDrug | ITreatment;

@Component({
  selector: 'custom-therapeutic-regime-update',
  templateUrl: './therapeutic-regime-update.component.html',
})
export class TherapeuticRegimeUpdateComponent implements OnInit, OnDestroy {
  isSaving = false;
  drugs: IDrug[] = [];
  treatments: ITreatment[] = [];
  eventSubscriber?: Subscription;
  data = Array<Select2Option>();

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    acronym: [],
    purpose: [null, [Validators.required]],
    condition: [null, [Validators.required]],
    timing: [],
    indication: [null, [Validators.required]],
    criteria: [null, [Validators.required]],
    notice: [],
    drugs: [null, [Validators.required]],
    treatment: [null, Validators.required],
  });

  constructor(
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected drugService: DrugService,
    protected treatmentService: TreatmentService,
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    protected eventManager: JhiEventManager,
    private fb: FormBuilder,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.activeModal) {
      return;
    }
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.updateForm(therapeuticRegime);

      this.drugService.query().subscribe((res: HttpResponse<IDrug[]>) => (this.drugs = res.body ?? []));

      this.treatmentService.query().subscribe((res: HttpResponse<ITreatment[]>) => (this.treatments = res.body ?? []));
    });
    this.registerChangeInTherapeuticRegimes();

    this.getSelect2Options(this.drugs);
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
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
      drugs: therapeuticRegime.drugs,
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
      drugs: this.select2ToDrugEntity(this.editForm.get(['ad'])!.value),
      treatment: this.editForm.get(['treatment'])!.value,
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

  trackById(index: number, item: SelectableEntity): number {
    return item.id!;
  }

  getSelected(option: IDrug, selectedVals?: IDrug[]): IDrug {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  registerChangeInTherapeuticRegimes(): void {
    this.eventSubscriber = this.eventManager.subscribe('therapeuticRegimeListUpdate', () => this.previousState());
  }

  cancel(): void {
    this.modalService.open(TherapeuticRegimeCancelDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
  }

  getSelect2Options(options: IDrug[]): Array<Select2Option> {
    const data = Array<Select2Option>();
    options.forEach(value => {
      data.push({
        id: value.id?.toString(),
        value: value.name!,
        label: value.name!,
      });
    });
    return data;
  }
  select2ToDrugEntity(options: Array<Select2Option>): IDrug[] {
    const data: IDrug[] = [];
    options.forEach(value => {
      this.drugService.find(+value.id!).subscribe((res: HttpResponse<IDrug>) => data.push(res.body!));
    });
    return data;
  }
}
