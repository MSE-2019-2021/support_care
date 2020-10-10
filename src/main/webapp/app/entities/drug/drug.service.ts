import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDrug } from 'app/shared/model/drug.model';

type EntityResponseType = HttpResponse<IDrug>;
type EntityArrayResponseType = HttpResponse<IDrug[]>;

@Injectable({ providedIn: 'root' })
export class DrugService {
  public resourceUrl = SERVER_API_URL + 'api/drugs';

  constructor(protected http: HttpClient) {}

  create(drug: IDrug): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(drug);
    return this.http
      .post<IDrug>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(drug: IDrug): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(drug);
    return this.http
      .put<IDrug>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDrug>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDrug[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(drug: IDrug): IDrug {
    const copy: IDrug = Object.assign({}, drug, {
      createDate: drug.createDate && drug.createDate.isValid() ? drug.createDate.toJSON() : undefined,
      updateDate: drug.updateDate && drug.updateDate.isValid() ? drug.updateDate.toJSON() : undefined,
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
      res.body.forEach((drug: IDrug) => {
        drug.createDate = drug.createDate ? moment(drug.createDate) : undefined;
        drug.updateDate = drug.updateDate ? moment(drug.updateDate) : undefined;
      });
    }
    return res;
  }
}
