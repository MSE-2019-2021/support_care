import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INotice, Notice } from '../notice.model';
import { NoticeService } from '../service/notice.service';
import { IActiveSubstance } from 'app/entities/active-substance/active-substance.model';
import { ActiveSubstanceService } from 'app/entities/active-substance/service/active-substance.service';

@Component({
  selector: 'custom-notice-update',
  templateUrl: './notice-update.component.html',
})
export class NoticeUpdateComponent implements OnInit {
  isSaving = false;
  activesubstances: IActiveSubstance[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required, Validators.maxLength(1000)]],
    evaluation: [null, [Validators.required, Validators.maxLength(1000)]],
    intervention: [null, [Validators.required, Validators.maxLength(1000)]],
    activeSubstance: [null, Validators.required],
  });

  constructor(
    protected noticeService: NoticeService,
    protected activeSubstanceService: ActiveSubstanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notice }) => {
      this.updateForm(notice);

      this.activeSubstanceService.query().subscribe((res: HttpResponse<IActiveSubstance[]>) => (this.activesubstances = res.body ?? []));
    });
  }

  updateForm(notice: INotice): void {
    this.editForm.patchValue({
      id: notice.id,
      description: notice.description,
      evaluation: notice.evaluation,
      intervention: notice.intervention,
      activeSubstance: notice.activeSubstance,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notice = this.createFromForm();
    if (notice.id !== undefined) {
      this.subscribeToSaveResponse(this.noticeService.update(notice));
    } else {
      this.subscribeToSaveResponse(this.noticeService.create(notice));
    }
  }

  private createFromForm(): INotice {
    return {
      ...new Notice(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      evaluation: this.editForm.get(['evaluation'])!.value,
      intervention: this.editForm.get(['intervention'])!.value,
      activeSubstance: this.editForm.get(['activeSubstance'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotice>>): void {
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

  trackActiveSubstanceById(index: number, item: IActiveSubstance): number {
    return item.id!;
  }
}
