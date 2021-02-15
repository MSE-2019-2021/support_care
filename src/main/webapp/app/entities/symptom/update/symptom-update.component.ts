import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { FormArray, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISymptom, Symptom } from '../symptom.model';
import { SymptomService } from '../service/symptom.service';
import { ITherapeuticRegime } from 'app/entities/therapeutic-regime/therapeutic-regime.model';
import { TherapeuticRegimeService } from 'app/entities/therapeutic-regime/service/therapeutic-regime.service';
import { IOutcome } from 'app/entities/outcome/outcome.model';
import { OutcomeService } from 'app/entities/outcome/service/outcome.service';
import { IToxicityRate } from 'app/entities/toxicity-rate/toxicity-rate.model';
import { ToxicityRateService } from 'app/entities/toxicity-rate/service/toxicity-rate.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SymptomCancelDialogComponent } from 'app/entities/symptom/cancel/symptom-cancel-dialog.component';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'custom-symptom-update',
  templateUrl: './symptom-update.component.html',
})
export class SymptomUpdateComponent implements OnInit {
  isSaving = false;
  therapeuticregimes: ITherapeuticRegime[] = [];
  outcomes: IOutcome[] = [];
  toxicityrates: IToxicityRate[] = [];
  dropdownList: { id: number; text: string }[] = [];
  dropdownSettings = {};
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
    notice: [null, [Validators.maxLength(1000)]],
    therapeuticRegimes: [null, Validators.required],
    outcomes: [null, Validators.required],
    toxicityRates: new FormArray([]),
  });

  constructor(
    protected symptomService: SymptomService,
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected outcomeService: OutcomeService,
    protected toxicityRateService: ToxicityRateService,
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    private fb: FormBuilder,
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
    };
    this.activatedRoute.data.subscribe(({ symptom }) => {
      this.updateForm(symptom);

      this.therapeuticRegimeService
        .query()
        .subscribe((res: HttpResponse<ITherapeuticRegime[]>) => (this.therapeuticregimes = res.body ?? []));

      this.outcomeService.query().subscribe((res: HttpResponse<IOutcome[]>) => (this.outcomes = res.body ?? []));

      this.toxicityRateService.query().subscribe((res: HttpResponse<IOutcome[]>) => (this.toxicityrates = res.body ?? []));
    });
  }

  updateForm(symptom: ISymptom): void {
    this.editForm.patchValue({
      id: symptom.id,
      name: symptom.name,
      notice: symptom.notice,
      therapeuticRegimes: this.getSelectedTherapeuticRegime(symptom.therapeuticRegimes!),
      outcomes: this.getSelectedOutcome(symptom.outcomes!),
    });
    this.editForm.setControl('toxicityRates', this.fb.array(this.getToxicityRates().controls));
    if (symptom.id) {
      this.addToxicityRate(symptom.toxicityRates, false);
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const symptom = this.createFromForm();
    if (symptom.id !== undefined) {
      this.subscribeToSaveResponse(this.symptomService.update(symptom));
    } else {
      this.subscribeToSaveResponse(this.symptomService.create(symptom));
    }
  }

  isEditing(): boolean {
    const symptom = this.createFromForm();
    return !!symptom.id;
  }

  private createFromForm(): ISymptom {
    return {
      ...new Symptom(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      notice: this.editForm.get(['notice'])!.value,
      therapeuticRegimes: this.editForm.get(['therapeuticRegimes'])!.value,
      outcomes: this.editForm.get(['outcomes'])!.value,
      toxicityRates: this.editForm.get(['toxicityRates'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISymptom>>): void {
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

  loadAllTR(): void {
    this.isLoading = true;

    this.therapeuticRegimeService
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
        (res: HttpResponse<ITherapeuticRegime[]>) => {
          this.isLoading = false;
          this.paginateTherapeuticRegimes(res.body, res.headers);
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

  resetTR(): void {
    this.page = 0;
    this.therapeuticregimes = [];
    this.loadAllTR();
  }

  loadPageTR(page: number): void {
    this.page = page;
    this.loadAllTR();
  }

  trackId(index: number, item: ITherapeuticRegime): number {
    return item.id!;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTherapeuticRegimes(data: ITherapeuticRegime[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.therapeuticregimes.push(data[i]);
      }
    }
  }

  searchingTR(item: any): any {
    clearTimeout(this.timer);
    this.searchName = item;

    this.timer = setTimeout(() => {
      this.resetTR();
    }, 200);
  }

  loadAllO(): void {
    this.isLoading = true;

    this.outcomeService
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
        (res: HttpResponse<IOutcome[]>) => {
          this.isLoading = false;
          this.paginateOutcomes(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  resetO(): void {
    this.page = 0;
    this.outcomes = [];
    this.loadAllO();
  }

  loadPageO(page: number): void {
    this.page = page;
    this.loadAllO();
  }

  protected paginateOutcomes(data: IOutcome[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.outcomes.push(data[i]);
      }
    }
  }

  searchingO(item: any): any {
    clearTimeout(this.timer);
    this.searchName = item;

    this.timer = setTimeout(() => {
      this.resetO();
    }, 200);
  }

  getSelectedTherapeuticRegime(options: ITherapeuticRegime[]): { id: number; text: string }[] {
    const dropdownList: { id: number; text: string }[] = [];
    if (typeof options !== 'undefined' && options.length > 0) {
      options.forEach(value => {
        dropdownList.push({
          id: value.id!,
          text: value.name!,
        });
      });
    }
    return dropdownList;
  }

  getSelectedOutcome(options: IOutcome[]): { id: number; text: string }[] {
    const dropdownList: { id: number; text: string }[] = [];
    if (typeof options !== 'undefined' && options.length > 0) {
      options.forEach(value => {
        dropdownList.push({
          id: value.id!,
          text: value.name!,
        });
      });
    }
    return dropdownList;
  }

  getToxicityRates(): FormArray {
    return this.editForm.controls.toxicityRates as FormArray;
  }

  addToxicityRate(toxicityRate: any = {}, newToxicityRate: boolean = true): void {
    const currentSymptom = this.editForm.controls;
    const currentToxicityRates = currentSymptom.toxicityRates as FormArray;
    if (typeof toxicityRate !== 'undefined' && newToxicityRate) {
      currentToxicityRates.push(
        this.fb.group({
          id: [null, []],
          name: ['', [Validators.required, Validators.maxLength(1000)]],
          description: ['', [Validators.maxLength(1000)]],
          notice: ['', [Validators.maxLength(1000)]],
          autonomousIntervention: ['', [Validators.maxLength(1000)]],
          interdependentIntervention: [],
          selfManagement: [],
        })
      );
    } else {
      toxicityRate.forEach(
        (obj: {
          id: null;
          name: '';
          description: '';
          notice: '';
          autonomousIntervention: '';
          interdependentIntervention: '';
          selfManagement: '';
        }) => {
          currentToxicityRates.push(
            this.fb.group({
              id: [obj.id, []],
              name: [obj.name, [Validators.required, Validators.maxLength(1000)]],
              description: [obj.description, [Validators.maxLength(1000)]],
              notice: [obj.notice, [Validators.maxLength(1000)]],
              autonomousIntervention: [obj.selfManagement, [Validators.maxLength(1000)]],
              interdependentIntervention: [obj.interdependentIntervention, [Validators.maxLength(1000)]],
              selfManagement: [obj.selfManagement, [Validators.maxLength(1000)]],
            })
          );
        }
      );
    }
  }

  deleteToxicityRate(i: number): void {
    const currentSymptom = this.editForm.controls;
    const currentToxicityRates = currentSymptom.toxicityRates as FormArray;
    currentToxicityRates.removeAt(i);
  }

  cancel(): void {
    this.modalService.open(SymptomCancelDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
  }
}
