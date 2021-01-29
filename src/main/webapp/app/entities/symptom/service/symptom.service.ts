import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { ISymptom } from '../symptom.model';
import * as dayjs from 'dayjs';
import { map } from 'rxjs/operators';

export type EntityResponseType = HttpResponse<ISymptom>;
export type EntityArrayResponseType = HttpResponse<ISymptom[]>;

@Injectable({ providedIn: 'root' })
export class SymptomService {
  public resourceUrl = SERVER_API_URL + 'api/symptoms';

  constructor(protected http: HttpClient) {}

  create(symptom: ISymptom): Observable<EntityResponseType> {
    return this.http
      .post<ISymptom>(this.resourceUrl, symptom, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(symptom: ISymptom): Observable<EntityResponseType> {
    return this.http
      .put<ISymptom>(this.resourceUrl, symptom, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISymptom>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISymptom[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((symptom: ISymptom) => {
        symptom.createdDate = symptom.createdDate ? dayjs(symptom.createdDate) : undefined;
        symptom.lastModifiedDate = symptom.lastModifiedDate ? dayjs(symptom.lastModifiedDate) : undefined;
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
