import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActiveSubstance } from '../active-substance.model';
import { ActiveSubstanceDeleteDialogComponent } from '../delete/active-substance-delete-dialog.component';

@Component({
  selector: 'custom-active-substance-detail',
  templateUrl: './active-substance-detail.component.html',
})
export class ActiveSubstanceDetailComponent implements OnInit {
  activeSubstance: IActiveSubstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activeSubstance }) => {
      this.activeSubstance = activeSubstance;
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(activeSubstance: IActiveSubstance): void {
    const modalRef = this.modalService.open(ActiveSubstanceDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.activeSubstance = activeSubstance;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }
}
