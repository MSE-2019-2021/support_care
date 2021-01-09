import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IFeedback, Feedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';

@Component({
  selector: 'custom-create-feedback-dialog',
  templateUrl: './define-reason-dialog.component.html',
})
export class DefineReasonDialogComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    entityName: [null],
    entityId: [null],
    thumb: [null],
    reason: [null, [Validators.maxLength(1000)]],
    solved: [null],
    anonym: [null, [Validators.required]],
  });

  constructor(
    protected feedbackService: FeedbackService,
    public activeModal: NgbActiveModal,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
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

  cancel(): void {
    const feedback = this.createFromForm(false);
    this.saveFeedback(feedback);
  }

  confirm(): void {
    const feedback = this.createFromForm(true);
    this.saveFeedback(feedback);
  }

  private createFromForm(keepReasonAndAnonym: boolean): IFeedback {
    return {
      ...new Feedback(),
      id: this.editForm.get(['id'])!.value,
      entityName: this.editForm.get(['entityName'])!.value,
      entityId: this.editForm.get(['entityId'])!.value,
      thumb: this.editForm.get(['thumb'])!.value,
      reason: keepReasonAndAnonym ? this.editForm.get(['reason'])!.value : null,
      solved: this.editForm.get(['solved'])!.value,
      anonym: keepReasonAndAnonym ? this.editForm.get(['anonym'])!.value : false,
    };
  }

  protected saveFeedback(feedback: IFeedback): void {
    // Update/Delete user Feedback
    this.feedbackService.manageFeedbackFromEntity(feedback).subscribe(
      () => {
        this.activeModal.close('saved');
      },
      () => {
        this.activeModal.dismiss();
      }
    );
  }
}
