import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdministration } from 'app/shared/model/administration.model';
import { AdministrationDeleteDialogComponent } from 'app/entities/administration/delete/administration-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'custom-administration-detail',
  templateUrl: './administration-detail.component.html',
})
export class AdministrationDetailComponent implements OnInit {
  administration: IAdministration | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ administration }) => {
      this.administration = administration;
    });
  }

  previousState(): void {
    window.history.back();
  }

  delete(administration: IAdministration): void {
    const modalRef = this.modalService.open(AdministrationDeleteDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.administration = administration;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }
}
