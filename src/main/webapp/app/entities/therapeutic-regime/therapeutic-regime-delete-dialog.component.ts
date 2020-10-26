import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from './therapeutic-regime.service';

@Component({
  templateUrl: './therapeutic-regime-delete-dialog.component.html',
})
export class TherapeuticRegimeDeleteDialogComponent {
  therapeuticRegime?: ITherapeuticRegime;

  constructor(
    protected therapeuticRegimeService: TherapeuticRegimeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.therapeuticRegimeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('therapeuticRegimeListModification');
      this.activeModal.close();
    });
  }
}
