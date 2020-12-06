import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeedback, Feedback } from 'app/shared/model/feedback.model';
import { FeedbackService } from '../feedback.service';

@Component({
  selector: 'custom-feedback-update',
  templateUrl: './feedback-update.component.html',
})
export class FeedbackUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    entityName: [null, [Validators.required]],
    entityId: [null, [Validators.required]],
    thumb: [null, [Validators.required]],
    reason: [null, [Validators.maxLength(1000)]],
    solved: [null, [Validators.required]],
    anonym: [null, [Validators.required]],
  });

  constructor(
    protected feedbackService: FeedbackService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.activeModal) {
      return;
    }
    this.activatedRoute.data.subscribe(({ feedback }) => {
      this.updateForm(feedback);
    });
  }

  updateForm(feedback: IFeedback): void {
    this.editForm.patchValue({
      id: feedback.id,
      entityName: feedback.entityName,
      entityId: feedback.entityId,
      thumb: feedback.thumb,
      reason: feedback.reason,
      solved: feedback.solved,
      anonym: feedback.anonym,
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
    const feedback = this.createFromForm();
    if (feedback.id !== undefined) {
      this.subscribeToSaveResponse(this.feedbackService.update(feedback));
    } else {
      this.subscribeToSaveResponse(this.feedbackService.create(feedback));
    }
  }

  private createFromForm(): IFeedback {
    return {
      ...new Feedback(),
      id: this.editForm.get(['id'])!.value,
      entityName: this.editForm.get(['entityName'])!.value,
      entityId: this.editForm.get(['entityId'])!.value,
      thumb: this.editForm.get(['thumb'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      solved: this.editForm.get(['solved'])!.value,
      anonym: this.editForm.get(['anonym'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedback>>): void {
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
