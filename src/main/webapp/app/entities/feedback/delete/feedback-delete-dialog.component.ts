import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FeedbackService } from '../service/feedback.service';

@Component({
  templateUrl: './feedback-delete-dialog.component.html',
})
export class FeedbackDeleteDialogComponent {
  constructor(protected feedbackService: FeedbackService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(): void {
    this.feedbackService.deleteSolved().subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
