import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from './therapeutic-regime.service';
import { TherapeuticRegimeDeleteDialogComponent } from 'app/entities/therapeutic-regime/therapeutic-regime-delete-dialog.component';

@Component({
  selector: 'custom-therapeutic-regime-detail',
  templateUrl: './therapeutic-regime-detail.component.html',
  styleUrls: ['therapeutic-regime.scss'],
})
export class TherapeuticRegimeDetailComponent implements OnInit, OnDestroy {
  therapeuticRegime: ITherapeuticRegime | null = null;
  eventSubscriber?: Subscription;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ therapeuticRegime }) => {
      this.therapeuticRegime = therapeuticRegime;
    });
    this.registerChangeInTherapeuticRegimes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  previousState(): void {
    window.history.back();
  }

  registerChangeInTherapeuticRegimes(): void {
    this.eventSubscriber = this.eventManager.subscribe('therapeuticRegimeViewModification', () => this.previousState());
  }

  delete(therapeuticRegime: ITherapeuticRegime): void {
    const modalRef = this.modalService.open(TherapeuticRegimeDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.therapeuticRegime = therapeuticRegime;
    modalRef.componentInstance.eventName = 'therapeuticRegimeViewModification';
  }
}
