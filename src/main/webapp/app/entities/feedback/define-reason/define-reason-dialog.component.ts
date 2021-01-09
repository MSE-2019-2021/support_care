import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IFeedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';

@Component({
  selector: 'custom-create-feedback-dialog',
  templateUrl: './define-reason-dialog.component.html',
})
export class DefineReasonDialogComponent implements OnInit {
  feedback?: IFeedback;
  isSaving = false;

  editForm = this.fb.group({
    reason: [null, [Validators.maxLength(1000)]],
    anonym: [null, [Validators.required]],
  });

  constructor(protected feedbackService: FeedbackService, public activeModal: NgbActiveModal, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.updateForm(this.feedback!);
  }

  updateForm(feedback: IFeedback): void {
    this.editForm.patchValue({
      reason: feedback.reason,
      anonym: feedback.anonym,
    });
  }

  cancel(): void {
    this.saveFeedback(this.feedback!);
  }

  confirm(): void {
    this.feedback!.reason = this.editForm.get(['reason'])!.value;
    this.feedback!.anonym = this.editForm.get(['anonym'])!.value;
    this.saveFeedback(this.feedback!);
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
