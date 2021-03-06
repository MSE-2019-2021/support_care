import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

import { ITherapeuticRegime, TherapeuticRegime } from '../therapeutic-regime.model';
import { TherapeuticRegimeCancelDialogComponent } from '../cancel/therapeutic-regime-cancel-dialog.component';
import { TherapeuticRegimeService } from '../service/therapeutic-regime.service';
import { IActiveSubstance } from 'app/entities/active-substance/active-substance.model';
import { ActiveSubstanceService } from 'app/entities/active-substance/service/active-substance.service';
import { ITreatment } from 'app/entities/treatment/treatment.model';
import { TreatmentService } from 'app/entities/treatment/service/treatment.service';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'custom-therapeutic-regime-update',
  templateUrl: './therapeutic-regime-update.component.html',
})
export class TherapeuticRegimeUpdateComponent implements OnInit {
  isSaving = false;
  activeSubstances: IActiveSubstance[] = [];
  treatments: ITreatment[] = [];
  itemsPerPage: number;
  links: { [key: string]: number };
  isLoading = false;
  page: number;
  predicate: string;
  ascending: boolean;
  searchName: string | undefined;
  timer: ReturnType<typeof setTimeout> = setTimeout(() => '', 200);

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    acronym: [null, [Validators.maxLength(50)]],
    purpose: [null, [Validators.required, Validators.maxLength(3000)]],
    condition: [null, [Validators.required, Validators.maxLength(3000)]],
    timing: [null, [Validators.maxLength(255)]],
    indication: [null, [Validators.required, Validators.maxLength(3000)]],
    criteria: [null, [Validators.required, Validators.maxLength(3000)]],
    notice: [null, [Validators.maxLength(3000)]],
    activeSubstances: [null, Validators.required],
    treatment: [null, Validators.required],
  });

  dropdownList: { id: number; text: string }[] = [];
  dropdownSettings = {};

  constructor(
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected activeSubstanceService: ActiveSubstanceService,
    protected treatmentService: TreatmentService,
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    private fb: FormBuilder,
    private route: Router,
    protected parseLinks: ParseLinks
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.links = {
      last: 0,
    };
    this.page = 0;
    this.predicate = 'name';
    this.ascending = true;
  }

  ngOnInit(): void {
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true,
      allowRemoteDataSearch: true,
    };
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.updateForm(therapeuticRegime);

      this.activeSubstanceService.query().subscribe((res: HttpResponse<IActiveSubstance[]>) => (this.activeSubstances = res.body ?? []));

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
      activeSubstances: this.getSelectedActiveSubstance(therapeuticRegime.activeSubstances!),
      treatment: therapeuticRegime.treatment,
    });
  }

  previousState(): void {
    window.history.back();
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

  isEditing(): boolean {
    const activeSubstance = this.createFromForm();
    return !!activeSubstance.id;
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
      activeSubstances: this.editForm.get(['activeSubstances'])!.value,
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

  trackTreatmentById(index: number, item: ITreatment): number {
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

  getSelectedActiveSubstance(options: IActiveSubstance[]): { id: number; text: string }[] {
    const dropdownList: { id: number; text: string }[] = [];

    if (typeof options !== 'undefined' && options.length > 0) {
      options.forEach(value => {
        dropdownList.push({
          id: value.id!,
          text: value.name! + ' - ' + value.dosage! + ' (' + value.form! + ')',
        });
      });
    }

    return dropdownList;
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  loadAll(): void {
    this.isLoading = true;

    this.activeSubstanceService
      .query(
        Object.assign(
          {},
          {
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
          },
          this.getCriteria()
        )
      )
      .subscribe(
        (res: HttpResponse<IActiveSubstance[]>) => {
          this.isLoading = false;
          this.paginateActiveSubstances(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  getCriteria(): {} {
    let criteria = {};

    if (this.searchName) {
      criteria = { 'name.contains': this.searchName };
    }
    return criteria;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateActiveSubstances(data: IActiveSubstance[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.activeSubstances.push(data[i]);
      }
    }
  }
  reset(): void {
    this.page = 0;
    this.activeSubstances = [];
    this.loadAll();
  }

  searching(item: any): any {
    this.searchName = item;
    clearTimeout(this.timer);

    this.timer = setTimeout(() => {
      this.reset();
    }, 200);
  }
}
