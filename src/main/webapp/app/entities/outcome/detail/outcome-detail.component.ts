import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { saveAs } from 'file-saver';

import { IOutcome } from '../outcome.model';
import { DocumentService } from 'app/entities/document/service/document.service';
import { IDocument } from 'app/entities/document/document.model';

@Component({
  selector: 'custom-outcome-detail',
  templateUrl: './outcome-detail.component.html',
})
export class OutcomeDetailComponent implements OnInit {
  outcome: IOutcome | null = null;

  constructor(private activatedRoute: ActivatedRoute, private documentService: DocumentService) {}

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
}
