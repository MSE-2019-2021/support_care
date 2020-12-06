import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdministration } from 'app/shared/model/administration.model';
import { AdministrationService } from '../administration.service';

@Component({
  templateUrl: './administration-delete-dialog.component.html',
})
export class AdministrationDeleteDialogComponent {
  administration?: IAdministration;

  constructor(protected administrationService: AdministrationService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.administrationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
