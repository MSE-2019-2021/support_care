import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IFeedback, Feedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';
import { Location } from '@angular/common';

@Component({
  selector: 'custom-create-feedback-dialog',
  templateUrl: './therapeutic-regime-create-feedback-dialog.component.html',
})
export class TherapeuticRegimeCreateFeedbackDialogComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    entityName: [null, [Validators.required]],
    entityId: [null, [Validators.required]],
    thumb: [null, [Validators.required]],
    reason: [null, [Validators.maxLength(1000)]],
    solved: [null, [Validators.required]],
    anonym: [null, [Validators.required]],
  });
  //therapeuticRegime.id
  constructor(
    private _location: Location,
    public activeModal: NgbActiveModal,
    protected feedbackService: FeedbackService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedback }) => {
      this.updateForm(feedback);
    });
  }

  updateForm(feedback: IFeedback): void {
    this.editForm.patchValue({
      id: feedback.id,
      entityName: 'therapeutic-regime',
      entityId: '1',
      thumb: false,
      reason: feedback.reason,
      solved: false,
      anonym: feedback.anonym,
    });
  }

  previousState(): void {
    window.history.back();
  }

  goBack(): void {
    this._location.back();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    this.isSaving = true;
    const feedback = this.createFromForm();
    if (feedback.id !== undefined) {
      this.subscribeToSaveResponse(this.feedbackService.update(feedback));
    } else {
      this.subscribeToSaveResponse(this.feedbackService.create(feedback));
    }
  }

  private createFromForm(): IFeedback {
    return {
      ...new Feedback(),
      id: this.editForm.get(['id'])!.value,
      entityName: this.editForm.get(['entityName'])!.value,
      entityId: this.editForm.get(['entityId'])!.value,
      thumb: this.editForm.get(['thumb'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      solved: this.editForm.get(['solved'])!.value,
      anonym: this.editForm.get(['anonym'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedback>>): void {
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
