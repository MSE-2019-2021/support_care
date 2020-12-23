import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: './settings-cancel-dialog.component.html',
})
export class SettingsCancelDialogComponent {
  constructor(public activeModal: NgbActiveModal, private _location: Location) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmCancel(): void {
    this.activeModal.close();
    window.history.back();
  }

  goBack(): void {
    this._location.back();
  }
}
