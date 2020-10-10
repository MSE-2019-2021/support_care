import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOutcome } from 'app/shared/model/outcome.model';

type EntityResponseType = HttpResponse<IOutcome>;
type EntityArrayResponseType = HttpResponse<IOutcome[]>;

@Injectable({ providedIn: 'root' })
export class OutcomeService {
  public resourceUrl = SERVER_API_URL + 'api/outcomes';

  constructor(protected http: HttpClient) {}

  create(outcome: IOutcome): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(outcome);
    return this.http
      .post<IOutcome>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(outcome: IOutcome): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(outcome);
    return this.http
      .put<IOutcome>(this.resourceUrl, copy, { observe: 'response' })
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

  protected convertDateFromClient(outcome: IOutcome): IOutcome {
    const copy: IOutcome = Object.assign({}, outcome, {
      createDate: outcome.createDate && outcome.createDate.isValid() ? outcome.createDate.toJSON() : undefined,
      updateDate: outcome.updateDate && outcome.updateDate.isValid() ? outcome.updateDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDate = res.body.createDate ? moment(res.body.createDate) : undefined;
      res.body.updateDate = res.body.updateDate ? moment(res.body.updateDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((outcome: IOutcome) => {
        outcome.createDate = outcome.createDate ? moment(outcome.createDate) : undefined;
        outcome.updateDate = outcome.updateDate ? moment(outcome.updateDate) : undefined;
      });
    }
    return res;
  }
}
