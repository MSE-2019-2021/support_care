import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDocument, Document } from '../document.model';
import { DocumentService } from '../service/document.service';
import { IContent } from 'app/entities/content/content.model';
import { ContentService } from 'app/entities/content/service/content.service';
import { IOutcome } from 'app/entities/outcome/outcome.model';
import { OutcomeService } from 'app/entities/outcome/service/outcome.service';

@Component({
  selector: 'custom-document-update',
  templateUrl: './document-update.component.html',
})
export class DocumentUpdateComponent implements OnInit {
  isSaving = false;
  contents: IContent[] = [];
  outcomes: IOutcome[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.maxLength(255)]],
    size: [null, [Validators.required]],
    mimeType: [null, [Validators.maxLength(50)]],
    content: [],
    outcome: [null, Validators.required],
  });

  constructor(
    protected documentService: DocumentService,
    protected contentService: ContentService,
    protected outcomeService: OutcomeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ document }) => {
      this.updateForm(document);

      this.contentService
        .query({ 'documentId.specified': 'false' })
        .pipe(map((res: HttpResponse<IContent[]>) => res.body ?? []))
        .subscribe((resBody: IContent[]) => {
          if (!document.content || !document.content.id) {
            this.contents = resBody;
          } else {
            this.contentService
              .find(document.content.id)
              .pipe(map((subRes: HttpResponse<IContent>) => (subRes.body ? [subRes.body].concat(resBody) : resBody)))
              .subscribe((concatRes: IContent[]) => (this.contents = concatRes));
          }
        });

      this.outcomeService.query().subscribe((res: HttpResponse<IOutcome[]>) => (this.outcomes = res.body ?? []));
    });
  }

  updateForm(document: IDocument): void {
    this.editForm.patchValue({
      id: document.id,
      title: document.title,
      size: document.size,
      mimeType: document.mimeType,
      content: document.content,
      outcome: document.outcome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const document = this.createFromForm();
    if (document.id !== undefined) {
      this.subscribeToSaveResponse(this.documentService.update(document));
    } else {
      this.subscribeToSaveResponse(this.documentService.create(document));
    }
  }

  private createFromForm(): IDocument {
    return {
      ...new Document(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      size: this.editForm.get(['size'])!.value,
      mimeType: this.editForm.get(['mimeType'])!.value,
      content: this.editForm.get(['content'])!.value,
      outcome: this.editForm.get(['outcome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackContentById(index: number, item: IContent): number {
    return item.id!;
  }

  trackOutcomeById(index: number, item: IOutcome): number {
    return item.id!;
  }
}
