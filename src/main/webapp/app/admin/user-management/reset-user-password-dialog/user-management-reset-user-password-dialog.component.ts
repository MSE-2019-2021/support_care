import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: './user-management-reset-user-password-dialog.component.html',
})
export class UserManagementResetUserPasswordDialogComponent {
  constructor(public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }
  // still needs changing, waiting on backend changes
  confirmReset(): void {
    this.activeModal.close('reset');
  }
}
