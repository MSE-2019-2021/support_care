import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOutcome } from 'app/shared/model/outcome.model';
import { OutcomeDeleteDialogComponent } from 'app/entities/outcome/outcome-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'custom-outcome-detail',
  templateUrl: './outcome-detail.component.html',
})
export class OutcomeDetailComponent implements OnInit {
  outcome: IOutcome | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcome }) => {
      this.outcome = outcome;
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(outcome: IOutcome): void {
    const modalRef = this.modalService.open(OutcomeDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.outcome = outcome;
  }
}
