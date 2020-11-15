import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDrug } from 'app/shared/model/drug.model';
import { DrugService } from './drug.service';
import { DrugDeleteDialogComponent } from './drug-delete-dialog.component';

@Component({
  selector: 'custom-drug-detail',
  templateUrl: './drug-detail.component.html',
  styleUrls: ['drug.scss'],
})
export class DrugDetailComponent implements OnInit, OnDestroy {
  drug: IDrug | null = null;
  eventSubscriber?: Subscription;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected drugService: DrugService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ drug }) => {
      this.drug = drug;
    });
    this.registerChangeInDrugs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  previousState(): void {
    window.history.back();
  }

  registerChangeInDrugs(): void {
    this.eventSubscriber = this.eventManager.subscribe('drugViewModification', () => this.previousState());
  }

  delete(drug: IDrug): void {
    const modalRef = this.modalService.open(DrugDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.drug = drug;
    modalRef.componentInstance.eventName = 'drugViewModification';
  }
}
