import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocument, Document } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';
import { IContent } from 'app/shared/model/content.model';
import { ContentService } from 'app/entities/content/content.service';
import { IOutcome } from 'app/shared/model/outcome.model';
import { OutcomeService } from 'app/entities/outcome/outcome.service';

type SelectableEntity = IContent | IOutcome;

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
    title: [null, [Validators.required]],
    size: [null, [Validators.required]],
    mimeType: [],
    content: [],
    outcome: [null, Validators.required],
  });

  constructor(
    protected documentService: DocumentService,
    protected contentService: ContentService,
    protected outcomeService: OutcomeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.activeModal) {
      return;
    }
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
              .find(document.contentId)
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
    if (this.activeModal) {
      this.activeModal.close();
    } else {
      window.history.back();
    }
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

  trackById(index: number, item: SelectableEntity): number {
    return item.id!;
  }
}
