import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGuide, Guide } from 'app/shared/model/guide.model';
import { GuideService } from './guide.service';

@Component({
  selector: 'jhi-guide-update',
  templateUrl: './guide-update.component.html',
})
export class GuideUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    createUser: [null, [Validators.required]],
    createDate: [null, [Validators.required]],
    updateUser: [null, [Validators.required]],
    updateDate: [null, [Validators.required]],
  });

  constructor(protected guideService: GuideService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guide }) => {
      if (!guide.id) {
        const today = moment().startOf('day');
        guide.createDate = today;
        guide.updateDate = today;
      }

      this.updateForm(guide);
    });
  }

  updateForm(guide: IGuide): void {
    this.editForm.patchValue({
      id: guide.id,
      description: guide.description,
      createUser: guide.createUser,
      createDate: guide.createDate ? guide.createDate.format(DATE_TIME_FORMAT) : null,
      updateUser: guide.updateUser,
      updateDate: guide.updateDate ? guide.updateDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const guide = this.createFromForm();
    if (guide.id !== undefined) {
      this.subscribeToSaveResponse(this.guideService.update(guide));
    } else {
      this.subscribeToSaveResponse(this.guideService.create(guide));
    }
  }

  private createFromForm(): IGuide {
    return {
      ...new Guide(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      createUser: this.editForm.get(['createUser'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      updateUser: this.editForm.get(['updateUser'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value ? moment(this.editForm.get(['updateDate'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuide>>): void {
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
