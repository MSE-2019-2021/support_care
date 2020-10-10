import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProtocol } from 'app/shared/model/protocol.model';
import { ProtocolService } from './protocol.service';

@Component({
  templateUrl: './protocol-delete-dialog.component.html',
})
export class ProtocolDeleteDialogComponent {
  protocol?: IProtocol;

  constructor(protected protocolService: ProtocolService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.protocolService.delete(id).subscribe(() => {
      this.eventManager.broadcast('protocolListModification');
      this.activeModal.close();
    });
  }
}
