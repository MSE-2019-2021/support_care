import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOutcome } from 'app/shared/model/outcome.model';
import { OutcomeService } from './outcome.service';

@Component({
  templateUrl: './outcome-delete-dialog.component.html',
})
export class OutcomeDeleteDialogComponent {
  outcome?: IOutcome;

  constructor(protected outcomeService: OutcomeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.outcomeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('outcomeListModification');
      this.activeModal.close();
    });
  }
}
