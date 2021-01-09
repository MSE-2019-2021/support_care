import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { saveAs } from 'file-saver';

import { IOutcome } from '../outcome.model';
import { DocumentService } from 'app/entities/document/service/document.service';
import { IDocument } from 'app/entities/document/document.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OutcomeDeleteDialogComponent } from 'app/entities/outcome/delete/outcome-delete-dialog.component';

@Component({
  selector: 'custom-outcome-detail',
  templateUrl: './outcome-detail.component.html',
})
export class OutcomeDetailComponent implements OnInit {
  outcome: IOutcome | null = null;

  constructor(private activatedRoute: ActivatedRoute, private documentService: DocumentService, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcome }) => {
      this.outcome = outcome;
    });
  }

  downloadDocument(document: IDocument): void {
    this.documentService.download(document.id!).subscribe(file => {
      saveAs(file, document.title);
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
