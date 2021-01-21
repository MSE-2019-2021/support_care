import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IOutcome } from '../outcome.model';
import * as dayjs from 'dayjs';
import { map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<IOutcome>;
type EntityArrayResponseType = HttpResponse<IOutcome[]>;

@Injectable({ providedIn: 'root' })
export class OutcomeService {
  public resourceUrl = SERVER_API_URL + 'api/outcomes';

  constructor(protected http: HttpClient) {}

  create(outcome: IOutcome, files?: FileList): Observable<EntityResponseType> {
    const outcomeMultipartFormParam = 'outcomeDTO';
    const filesMultipartFormParam = 'files';

    const formData: FormData = new FormData();
    const outcomeAsJsonBlob: Blob = new Blob([JSON.stringify(outcome)], { type: 'application/json' });

    formData.append(outcomeMultipartFormParam, outcomeAsJsonBlob);

    if (files?.length) {
      for (let i = 0; i < files.length; i++) {
        formData.append(filesMultipartFormParam, files.item(i)!);
      }
    }

    return this.http
      .post<IOutcome>(this.resourceUrl, formData, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(outcome: IOutcome, files?: FileList): Observable<EntityResponseType> {
    const outcomeMultipartFormParam = 'outcomeDTO';
    const filesMultipartFormParam = 'files';

    const formData: FormData = new FormData();
    const outcomeAsJsonBlob: Blob = new Blob([JSON.stringify(outcome)], { type: 'application/json' });

    formData.append(outcomeMultipartFormParam, outcomeAsJsonBlob);

    if (files?.length) {
      for (let i = 0; i < files.length; i++) {
        formData.append(filesMultipartFormParam, files.item(i)!);
      }
    }

    return this.http
      .put<IOutcome>(this.resourceUrl, formData, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOutcome>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOutcome[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((outcome: IOutcome) => {
        outcome.createdDate = outcome.createdDate ? dayjs(outcome.createdDate) : undefined;
        outcome.lastModifiedDate = outcome.lastModifiedDate ? dayjs(outcome.lastModifiedDate) : undefined;
      });
    }
    return res;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }
}
