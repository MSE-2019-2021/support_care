import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDrug } from 'app/shared/model/drug.model';
import { DrugDeleteDialogComponent } from '../delete/drug-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'custom-drug-detail',
  templateUrl: './drug-detail.component.html',
})
export class DrugDetailComponent implements OnInit {
  drug: IDrug | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ drug }) => {
      this.drug = drug;
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(drug: IDrug): void {
    const modalRef = this.modalService.open(DrugDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.drug = drug;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }
}
