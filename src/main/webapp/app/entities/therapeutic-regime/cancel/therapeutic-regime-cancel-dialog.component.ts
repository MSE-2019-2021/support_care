import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: './therapeutic-regime-cancel-dialog.component.html',
})
export class TherapeuticRegimeCancelDialogComponent {
  constructor(public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmCancel(): void {
    this.activeModal.close('canceled');
  }
}
