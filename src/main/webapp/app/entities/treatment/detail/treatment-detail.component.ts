import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITreatment } from 'app/shared/model/treatment.model';
import { TreatmentDeleteDialogComponent } from 'app/entities/treatment/delete/treatment-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'custom-treatment-detail',
  templateUrl: './treatment-detail.component.html',
})
export class TreatmentDetailComponent implements OnInit {
  treatment: ITreatment | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ treatment }) => {
      this.treatment = treatment;
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(treatment: ITreatment): void {
    const modalRef = this.modalService.open(TreatmentDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.treatment = treatment;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }
}
