import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IFeedback, Feedback } from 'app/entities/feedback/feedback.model';

import { Location } from '@angular/common';

@Component({
  selector: 'custom-create-feedback-dialog',
  templateUrl: './define-reason-dialog.component.html',
})
export class DefineReasonDialogComponent implements OnInit {
  isSaving = false;
  feedback: IFeedback;

  editForm = this.fb.group({
    reason: [null, [Validators.maxLength(1000)]],
    anonym: [null, [Validators.required]],
  });

  constructor(
    private _location: Location,
    public activeModal: NgbActiveModal,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.feedback = new Feedback();
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedback }) => {
      this.feedback = feedback;
      this.updateForm(feedback);
    });
  }

  updateForm(feedback: IFeedback): void {
    this.editForm.patchValue({
      reason: feedback.reason,
      anonym: feedback.anonym,
    });
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirm(): void {
    this.feedback.reason = this.editForm.get(['reason'])!.value;
    this.feedback.anonym = this.editForm.get(['anonym'])!.value;
    this.activeModal.close('confirmed');
  }
}
