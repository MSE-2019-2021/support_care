import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISymptom } from 'app/shared/model/symptom.model';
import { SymptomDeleteDialogComponent } from 'app/entities/symptom/delete/symptom-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'custom-symptom-detail',
  templateUrl: './symptom-detail.component.html',
})
export class SymptomDetailComponent implements OnInit {
  symptom: ISymptom | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptom }) => {
      this.symptom = symptom;
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(symptom: ISymptom): void {
    const modalRef = this.modalService.open(SymptomDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.symptom = symptom;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }
}
