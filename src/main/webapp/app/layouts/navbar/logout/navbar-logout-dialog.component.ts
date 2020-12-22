import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: './navbar-logout-dialog.component.html',
  styleUrls: ['./navbar-logout-dialog.component.scss'],
})
export class NavbarLogoutDialogComponent {
  constructor(public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmLogout(): void {
    this.activeModal.close('logout');
  }
}
