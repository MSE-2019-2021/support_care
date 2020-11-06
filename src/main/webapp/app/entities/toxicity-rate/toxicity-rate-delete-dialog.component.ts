import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IToxicityRate } from 'app/shared/model/toxicity-rate.model';
import { ToxicityRateService } from './toxicity-rate.service';

@Component({
  templateUrl: './toxicity-rate-delete-dialog.component.html',
})
export class ToxicityRateDeleteDialogComponent {
  toxicityRate?: IToxicityRate;

  constructor(
    protected toxicityRateService: ToxicityRateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.toxicityRateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('toxicityRateListModification');
      this.activeModal.close();
    });
  }
}
