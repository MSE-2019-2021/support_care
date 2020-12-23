import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOutcome, Outcome } from '../outcome.model';
import { OutcomeService } from '../service/outcome.service';

@Component({
  selector: 'custom-outcome-update',
  templateUrl: './outcome-update.component.html',
})
export class OutcomeUpdateComponent implements OnInit {
  isSaving = false;
  files: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    description: [null, [Validators.maxLength(1000)]],
    documents: [],
  });

  constructor(protected outcomeService: OutcomeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcome }) => {
      this.updateForm(outcome);
    });
  }

  handleFileInput(event: Event): void {
    this.files = (<HTMLInputElement>event.target).files!;
  }

  updateForm(outcome: IOutcome): void {
    this.editForm.patchValue({
      id: outcome.id,
      name: outcome.name,
      description: outcome.description,
      documents: outcome.documents,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const outcome = this.createFromForm();
    if (outcome.id !== undefined) {
      this.subscribeToSaveResponse(this.outcomeService.update(outcome, this.files));
    } else {
      this.subscribeToSaveResponse(this.outcomeService.create(outcome, this.files));
    }
  }

  private createFromForm(): IOutcome {
    return {
      ...new Outcome(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
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
}
