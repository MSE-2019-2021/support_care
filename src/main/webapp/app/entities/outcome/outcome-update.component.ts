import { Component, OnInit, Optional } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOutcome, Outcome } from 'app/shared/model/outcome.model';
import { OutcomeService } from './outcome.service';

@Component({
  selector: 'custom-outcome-update',
  templateUrl: './outcome-update.component.html',
})
export class OutcomeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
  });

  constructor(
    protected outcomeService: OutcomeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    @Optional() public activeModal?: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.activeModal) {
      return;
    }
    this.activatedRoute.data.subscribe(({ outcome }) => {
      this.updateForm(outcome);
    });
  }

  updateForm(outcome: IOutcome): void {
    this.editForm.patchValue({
      id: outcome.id,
      name: outcome.name,
      description: outcome.description,
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
    const outcome = this.createFromForm();
    if (outcome.id !== undefined) {
      this.subscribeToSaveResponse(this.outcomeService.update(outcome));
    } else {
      this.subscribeToSaveResponse(this.outcomeService.create(outcome));
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
