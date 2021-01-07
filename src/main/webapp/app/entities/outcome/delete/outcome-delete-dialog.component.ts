import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOutcome } from '../outcome.model';
import { OutcomeService } from '../service/outcome.service';

@Component({
  templateUrl: './outcome-delete-dialog.component.html',
})
export class OutcomeDeleteDialogComponent {
  outcome?: IOutcome;

  constructor(protected outcomeService: OutcomeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.outcomeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
      window.history.back();
    });
  }
}
