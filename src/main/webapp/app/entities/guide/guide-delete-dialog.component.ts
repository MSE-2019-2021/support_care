import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGuide } from 'app/shared/model/guide.model';
import { GuideService } from './guide.service';

@Component({
  templateUrl: './guide-delete-dialog.component.html',
})
export class GuideDeleteDialogComponent {
  guide?: IGuide;

  constructor(protected guideService: GuideService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.guideService.delete(id).subscribe(() => {
      this.eventManager.broadcast('guideListModification');
      this.activeModal.close();
    });
  }
}
