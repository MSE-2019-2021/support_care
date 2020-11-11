import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  templateUrl: './therapeutic-regime-cancel-dialog.component.html',
})
export class TherapeuticRegimeCancelDialogComponent {

  constructor(
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmCancel(): void {
      this.eventManager.broadcast('therapeuticRegimeListUpdate');
      this.activeModal.close();
      window.history.back();
  }
}
