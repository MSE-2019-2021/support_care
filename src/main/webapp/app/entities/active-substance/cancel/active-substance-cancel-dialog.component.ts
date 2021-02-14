import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: './active-substance-cancel-dialog.component.html',
})
export class ActiveSubstanceCancelDialogComponent {
  constructor(public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmCancel(): void {
    this.activeModal.close('canceled');
    window.history.back();
  }
}
