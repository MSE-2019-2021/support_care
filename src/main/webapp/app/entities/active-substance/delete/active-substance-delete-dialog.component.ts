import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IActiveSubstance } from '../active-substance.model';
import { ActiveSubstanceService } from '../service/active-substance.service';

@Component({
  templateUrl: './active-substance-delete-dialog.component.html',
})
export class ActiveSubstanceDeleteDialogComponent {
  activeSubstance?: IActiveSubstance;

  constructor(protected activeSubstanceService: ActiveSubstanceService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.activeSubstanceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
