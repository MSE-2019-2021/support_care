import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IToxicityRate } from 'app/shared/model/toxicity-rate.model';
import { ToxicityRateDeleteDialogComponent } from 'app/entities/toxicity-rate/delete/toxicity-rate-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'custom-toxicity-rate-detail',
  templateUrl: './toxicity-rate-detail.component.html',
})
export class ToxicityRateDetailComponent implements OnInit {
  toxicityRate: IToxicityRate | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ toxicityRate }) => {
      this.toxicityRate = toxicityRate;
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(toxicityRate: IToxicityRate): void {
    const modalRef = this.modalService.open(ToxicityRateDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.toxicityRate = toxicityRate;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }
}
