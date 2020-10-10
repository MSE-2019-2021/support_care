import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProtocol, Protocol } from 'app/shared/model/protocol.model';
import { ProtocolService } from './protocol.service';
import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from 'app/entities/therapeutic-regime/therapeutic-regime.service';
import { IOutcome } from 'app/shared/model/outcome.model';
import { OutcomeService } from 'app/entities/outcome/outcome.service';
import { IGuide } from 'app/shared/model/guide.model';
import { GuideService } from 'app/entities/guide/guide.service';

type SelectableEntity = ITherapeuticRegime | IOutcome | IGuide;

@Component({
  selector: 'jhi-protocol-update',
  templateUrl: './protocol-update.component.html',
})
export class ProtocolUpdateComponent implements OnInit {
  isSaving = false;
  therapeuticregimes: ITherapeuticRegime[] = [];
  outcomes: IOutcome[] = [];
  guides: IGuide[] = [];

  editForm = this.fb.group({
    id: [],
    toxicityDiagnosis: [null, [Validators.required]],
    createUser: [null, [Validators.required]],
    createDate: [null, [Validators.required]],
    updateUser: [null, [Validators.required]],
    updateDate: [null, [Validators.required]],
    therapeuticRegimeId: [],
    outcomeId: [],
    guideId: [],
  });

  constructor(
    protected protocolService: ProtocolService,
    protected therapeuticRegimeService: TherapeuticRegimeService,
    protected outcomeService: OutcomeService,
    protected guideService: GuideService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ protocol }) => {
      if (!protocol.id) {
        const today = moment().startOf('day');
        protocol.createDate = today;
        protocol.updateDate = today;
      }

      this.updateForm(protocol);

      this.therapeuticRegimeService
        .query({ 'protocolId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ITherapeuticRegime[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITherapeuticRegime[]) => {
          if (!protocol.therapeuticRegimeId) {
            this.therapeuticregimes = resBody;
          } else {
            this.therapeuticRegimeService
              .find(protocol.therapeuticRegimeId)
              .pipe(
                map((subRes: HttpResponse<ITherapeuticRegime>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITherapeuticRegime[]) => (this.therapeuticregimes = concatRes));
          }
        });

      this.outcomeService.query().subscribe((res: HttpResponse<IOutcome[]>) => (this.outcomes = res.body || []));

      this.guideService.query().subscribe((res: HttpResponse<IGuide[]>) => (this.guides = res.body || []));
    });
  }

  updateForm(protocol: IProtocol): void {
    this.editForm.patchValue({
      id: protocol.id,
      toxicityDiagnosis: protocol.toxicityDiagnosis,
      createUser: protocol.createUser,
      createDate: protocol.createDate ? protocol.createDate.format(DATE_TIME_FORMAT) : null,
      updateUser: protocol.updateUser,
      updateDate: protocol.updateDate ? protocol.updateDate.format(DATE_TIME_FORMAT) : null,
      therapeuticRegimeId: protocol.therapeuticRegimeId,
      outcomeId: protocol.outcomeId,
      guideId: protocol.guideId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const protocol = this.createFromForm();
    if (protocol.id !== undefined) {
      this.subscribeToSaveResponse(this.protocolService.update(protocol));
    } else {
      this.subscribeToSaveResponse(this.protocolService.create(protocol));
    }
  }

  private createFromForm(): IProtocol {
    return {
      ...new Protocol(),
      id: this.editForm.get(['id'])!.value,
      toxicityDiagnosis: this.editForm.get(['toxicityDiagnosis'])!.value,
      createUser: this.editForm.get(['createUser'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      updateUser: this.editForm.get(['updateUser'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value ? moment(this.editForm.get(['updateDate'])!.value, DATE_TIME_FORMAT) : undefined,
      therapeuticRegimeId: this.editForm.get(['therapeuticRegimeId'])!.value,
      outcomeId: this.editForm.get(['outcomeId'])!.value,
      guideId: this.editForm.get(['guideId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProtocol>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
