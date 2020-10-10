import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOutcome, Outcome } from 'app/shared/model/outcome.model';
import { OutcomeService } from './outcome.service';

@Component({
  selector: 'jhi-outcome-update',
  templateUrl: './outcome-update.component.html',
})
export class OutcomeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    score: [null, [Validators.min(0), Validators.max(4)]],
    createUser: [null, [Validators.required]],
    createDate: [null, [Validators.required]],
    updateUser: [null, [Validators.required]],
    updateDate: [null, [Validators.required]],
  });

  constructor(protected outcomeService: OutcomeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcome }) => {
      if (!outcome.id) {
        const today = moment().startOf('day');
        outcome.createDate = today;
        outcome.updateDate = today;
      }

      this.updateForm(outcome);
    });
  }

  updateForm(outcome: IOutcome): void {
    this.editForm.patchValue({
      id: outcome.id,
      description: outcome.description,
      score: outcome.score,
      createUser: outcome.createUser,
      createDate: outcome.createDate ? outcome.createDate.format(DATE_TIME_FORMAT) : null,
      updateUser: outcome.updateUser,
      updateDate: outcome.updateDate ? outcome.updateDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const outcome = this.createFromForm();
    if (outcome.id !== undefined) {
      this.subscribeToSaveResponse(this.outcomeService.update(outcome));
    } else {
      this.subscribeToSaveResponse(this.outcomeService.create(outcome));
    }
  }

  private createFromForm(): IOutcome {
    return {
      ...new Outcome(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      score: this.editForm.get(['score'])!.value,
      createUser: this.editForm.get(['createUser'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      updateUser: this.editForm.get(['updateUser'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value ? moment(this.editForm.get(['updateDate'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOutcome>>): void {
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
