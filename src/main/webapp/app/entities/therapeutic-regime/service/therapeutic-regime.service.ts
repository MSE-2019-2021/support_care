import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { ITherapeuticRegime } from '../therapeutic-regime.model';
import * as dayjs from 'dayjs';
import { map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<ITherapeuticRegime>;
type EntityArrayResponseType = HttpResponse<ITherapeuticRegime[]>;

@Injectable({ providedIn: 'root' })
export class TherapeuticRegimeService {
  public resourceUrl = SERVER_API_URL + 'api/therapeutic-regimes';

  constructor(protected http: HttpClient) {}

  create(therapeuticRegime: ITherapeuticRegime): Observable<EntityResponseType> {
    return this.http
      .post<ITherapeuticRegime>(this.resourceUrl, therapeuticRegime, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(therapeuticRegime: ITherapeuticRegime): Observable<EntityResponseType> {
    return this.http
      .put<ITherapeuticRegime>(this.resourceUrl, therapeuticRegime, { observe: 'response' })
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

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((therapeuticRegime: ITherapeuticRegime) => {
        therapeuticRegime.createdDate = therapeuticRegime.createdDate ? dayjs(therapeuticRegime.createdDate) : undefined;
        therapeuticRegime.lastModifiedDate = therapeuticRegime.lastModifiedDate ? dayjs(therapeuticRegime.lastModifiedDate) : undefined;
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
