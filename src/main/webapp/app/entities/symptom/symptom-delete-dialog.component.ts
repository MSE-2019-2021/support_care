import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISymptom } from 'app/shared/model/symptom.model';
import { SymptomService } from './symptom.service';

@Component({
  templateUrl: './symptom-delete-dialog.component.html',
})
export class SymptomDeleteDialogComponent {
  symptom?: ISymptom;
  eventName?: string;

  constructor(
    protected symptomService: SymptomService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
   ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.symptomService.delete(id).subscribe(() => {
      if (this.eventName) {
        this.eventManager.broadcast(this.eventName);
      }
      this.activeModal.close();
      window.history.back();
    });
  }
}
