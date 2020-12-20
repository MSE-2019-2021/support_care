import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { ITreatment } from '../treatment.model';

type EntityResponseType = HttpResponse<ITreatment>;
type EntityArrayResponseType = HttpResponse<ITreatment[]>;

@Injectable({ providedIn: 'root' })
export class TreatmentService {
  public resourceUrl = SERVER_API_URL + 'api/treatments';

  constructor(protected http: HttpClient) {}

  create(treatment: ITreatment): Observable<EntityResponseType> {
    return this.http.post<ITreatment>(this.resourceUrl, treatment, { observe: 'response' });
  }

  update(treatment: ITreatment): Observable<EntityResponseType> {
    return this.http.put<ITreatment>(this.resourceUrl, treatment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITreatment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITreatment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
