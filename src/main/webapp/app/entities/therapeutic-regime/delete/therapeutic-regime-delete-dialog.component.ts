import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITherapeuticRegime } from '../therapeutic-regime.model';
import { TherapeuticRegimeService } from '../service/therapeutic-regime.service';

@Component({
  templateUrl: './therapeutic-regime-delete-dialog.component.html',
})
export class TherapeuticRegimeDeleteDialogComponent {
  therapeuticRegime?: ITherapeuticRegime;

  constructor(protected therapeuticRegimeService: TherapeuticRegimeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.therapeuticRegimeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
      window.history.back();
    });
  }
}
