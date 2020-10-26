import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INotice, Notice } from 'app/shared/model/notice.model';
import { NoticeService } from './notice.service';
import { IDrug } from 'app/shared/model/drug.model';
import { DrugService } from 'app/entities/drug/drug.service';

@Component({
  selector: 'jhi-notice-update',
  templateUrl: './notice-update.component.html',
})
export class NoticeUpdateComponent implements OnInit {
  isSaving = false;
  drugs: IDrug[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    evaluation: [null, [Validators.required]],
    intervention: [null, [Validators.required]],
    drugId: [null, Validators.required],
  });

  constructor(
    protected noticeService: NoticeService,
    protected drugService: DrugService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notice }) => {
      this.updateForm(notice);

      this.drugService.query().subscribe((res: HttpResponse<IDrug[]>) => (this.drugs = res.body || []));
    });
  }

  updateForm(notice: INotice): void {
    this.editForm.patchValue({
      id: notice.id,
      description: notice.description,
      evaluation: notice.evaluation,
      intervention: notice.intervention,
      drugId: notice.drugId,
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
      drugId: this.editForm.get(['drugId'])!.value,
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

  trackById(index: number, item: IDrug): any {
    return item.id;
  }
}
