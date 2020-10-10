import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';

type EntityResponseType = HttpResponse<ITherapeuticRegime>;
type EntityArrayResponseType = HttpResponse<ITherapeuticRegime[]>;

@Injectable({ providedIn: 'root' })
export class TherapeuticRegimeService {
  public resourceUrl = SERVER_API_URL + 'api/therapeutic-regimes';

  constructor(protected http: HttpClient) {}

  create(therapeuticRegime: ITherapeuticRegime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(therapeuticRegime);
    return this.http
      .post<ITherapeuticRegime>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(therapeuticRegime: ITherapeuticRegime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(therapeuticRegime);
    return this.http
      .put<ITherapeuticRegime>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITherapeuticRegime>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITherapeuticRegime[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(therapeuticRegime: ITherapeuticRegime): ITherapeuticRegime {
    const copy: ITherapeuticRegime = Object.assign({}, therapeuticRegime, {
      createDate:
        therapeuticRegime.createDate && therapeuticRegime.createDate.isValid() ? therapeuticRegime.createDate.toJSON() : undefined,
      updateDate:
        therapeuticRegime.updateDate && therapeuticRegime.updateDate.isValid() ? therapeuticRegime.updateDate.toJSON() : undefined,
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
      res.body.forEach((therapeuticRegime: ITherapeuticRegime) => {
        therapeuticRegime.createDate = therapeuticRegime.createDate ? moment(therapeuticRegime.createDate) : undefined;
        therapeuticRegime.updateDate = therapeuticRegime.updateDate ? moment(therapeuticRegime.updateDate) : undefined;
      });
    }
    return res;
  }
}
