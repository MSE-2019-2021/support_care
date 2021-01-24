import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormArray, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOutcome, Outcome } from '../outcome.model';
import { OutcomeService } from '../service/outcome.service';
import { OutcomeCancelDialogComponent } from 'app/entities/outcome/cancel/outcome-cancel-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'custom-outcome-update',
  templateUrl: './outcome-update.component.html',
  styleUrls: ['./updateOutcome.scss'],
})
export class OutcomeUpdateComponent implements OnInit {
  isSaving = false;
  files = {};

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    description: [null, [Validators.maxLength(1000)]],
    link: [null, [Validators.maxLength(1000)]],
    documents: new FormArray([]),
  });

  constructor(
    protected outcomeService: OutcomeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcome }) => {
      this.updateForm(outcome);
    });
  }

  handleFileInput(event: Event): void {
    this.files = (<HTMLInputElement>event.target).files!;
  }

  getFiles(): any {
    return this.files;
  }

  updateForm(outcome: IOutcome): void {
    this.editForm.patchValue({
      id: outcome.id,
      name: outcome.name,
      description: outcome.description,
      link: outcome.link,
      documents: outcome.documents,
    });
    this.editForm.setControl('documents', this.fb.array(this.getDocuments().controls));
    if (outcome.id) {
      this.addDocument(outcome.documents, false);
    }
  }

  getDocuments(): FormArray {
    return this.editForm.controls.documents as FormArray;
  }

  removeDocument(id: number): void {
    const currentOutcome = this.editForm.controls;
    const currentDocuments = currentOutcome.documents as FormArray;
    currentDocuments.removeAt(id);
  }

  addDocument(document: any = {}, newDocument: boolean = true): void {
    const currentOutcome = this.editForm.controls;
    const currentDocuments = currentOutcome.documents as FormArray;
    if (!newDocument) {
      document.forEach((obj: { id: null; title: ''; size: ''; mimeType: ''; content: '' }) => {
        currentDocuments.push(
          this.fb.group({
            id: [obj.id],
            title: [obj.title],
            size: [obj.size],
            mimeType: [obj.mimeType],
            content: [obj.content],
          })
        );
      });
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const outcome = this.createFromForm();
    if (outcome.id !== undefined) {
      this.subscribeToSaveResponse(this.outcomeService.update(outcome, this.files as FileList));
    } else {
      this.subscribeToSaveResponse(this.outcomeService.create(outcome, this.files as FileList));
    }
  }

  isEditing(): boolean {
    const outcome = this.createFromForm();
    return !!outcome.id;
  }

  private createFromForm(): IOutcome {
    return {
      ...new Outcome(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      link: this.editForm.get(['link'])!.value,
      documents: this.editForm.get(['documents'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOutcome>>): void {
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

  cancel(): void {
    const modalRef = this.modalService.open(OutcomeCancelDialogComponent, { centered: true, size: 'lg', backdrop: 'static' });
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'canceled') {
        this.previousState();
      }
    });
  }
}
