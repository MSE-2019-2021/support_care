import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ISymptom } from 'app/shared/model/symptom.model';
import { SymptomService } from './symptom.service';
import { SymptomDeleteDialogComponent } from 'app/entities/symptom/symptom-delete-dialog.component';

@Component({
  selector: 'custom-symptom-detail',
  templateUrl: './symptom-detail.component.html',
})
export class SymptomDetailComponent implements OnInit {
  symptom: ISymptom | null = null;
  eventSubscriber?: Subscription;

  constructor(
    protected symptomService: SymptomService,
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    protected eventManager: JhiEventManager,
   ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ symptom }) => {
      this.symptom = symptom;
    });
    this.registerChangeInSymptom();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  registerChangeInSymptom(): void {
    this.eventSubscriber = this.eventManager.subscribe('SymptomViewModification', () => this.previousState());
  }

  previousState(): void {
    window.history.back();
  }

  delete(symptom: ISymptom): void {
      const modalRef = this.modalService.open(SymptomDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
      modalRef.componentInstance.symptom = symptom;
      modalRef.componentInstance.eventName = 'symptomViewModification';
    }
}
