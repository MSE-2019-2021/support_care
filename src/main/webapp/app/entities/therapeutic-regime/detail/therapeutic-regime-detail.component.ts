import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITherapeuticRegime } from '../therapeutic-regime.model';
import { TherapeuticRegimeDeleteDialogComponent } from '../delete/therapeutic-regime-delete-dialog.component';
import { DefineReasonDialogComponent } from '../../feedback/define-reason/define-reason-dialog.component';
import { IFeedback, Feedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';

@Component({
  selector: 'custom-therapeutic-regime-detail',
  templateUrl: './therapeutic-regime-detail.component.html',
})
export class TherapeuticRegimeDetailComponent implements OnInit {
  therapeuticRegime: ITherapeuticRegime | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal, protected feedbackService: FeedbackService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.therapeuticRegime = therapeuticRegime;
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(therapeuticRegime: ITherapeuticRegime): void {
    const modalRef = this.modalService.open(TherapeuticRegimeDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.therapeuticRegime = therapeuticRegime;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }

  createFeedback(): void {
    this.modalService.open(DefineReasonDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
  }
}
