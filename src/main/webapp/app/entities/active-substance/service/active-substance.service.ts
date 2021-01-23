import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IActiveSubstance } from '../active-substance.model';
import * as dayjs from 'dayjs';
import { map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<IActiveSubstance>;
type EntityArrayResponseType = HttpResponse<IActiveSubstance[]>;

@Injectable({ providedIn: 'root' })
export class ActiveSubstanceService {
  public resourceUrl = SERVER_API_URL + 'api/active-substances';

  constructor(protected http: HttpClient) {}

  create(activeSubstance: IActiveSubstance): Observable<EntityResponseType> {
    return this.http
      .post<IActiveSubstance>(this.resourceUrl, activeSubstance, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(activeSubstance: IActiveSubstance): Observable<EntityResponseType> {
    return this.http
      .put<IActiveSubstance>(this.resourceUrl, activeSubstance, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IActiveSubstance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IActiveSubstance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((activeSubstance: IActiveSubstance) => {
        activeSubstance.createdDate = activeSubstance.createdDate ? dayjs(activeSubstance.createdDate) : undefined;
        activeSubstance.lastModifiedDate = activeSubstance.lastModifiedDate ? dayjs(activeSubstance.lastModifiedDate) : undefined;
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
