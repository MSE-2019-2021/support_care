import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IToxicityRate } from '../toxicity-rate.model';
import { ToxicityRateService } from '../service/toxicity-rate.service';

@Component({
  templateUrl: './toxicity-rate-delete-dialog.component.html',
})
export class ToxicityRateDeleteDialogComponent {
  toxicityRate?: IToxicityRate;

  constructor(protected toxicityRateService: ToxicityRateService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.toxicityRateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
