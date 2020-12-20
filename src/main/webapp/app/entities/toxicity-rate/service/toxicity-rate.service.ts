import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IToxicityRate } from '../toxicity-rate.model';

type EntityResponseType = HttpResponse<IToxicityRate>;
type EntityArrayResponseType = HttpResponse<IToxicityRate[]>;

@Injectable({ providedIn: 'root' })
export class ToxicityRateService {
  public resourceUrl = SERVER_API_URL + 'api/toxicity-rates';

  constructor(protected http: HttpClient) {}

  create(toxicityRate: IToxicityRate): Observable<EntityResponseType> {
    return this.http.post<IToxicityRate>(this.resourceUrl, toxicityRate, { observe: 'response' });
  }

  update(toxicityRate: IToxicityRate): Observable<EntityResponseType> {
    return this.http.put<IToxicityRate>(this.resourceUrl, toxicityRate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IToxicityRate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IToxicityRate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
