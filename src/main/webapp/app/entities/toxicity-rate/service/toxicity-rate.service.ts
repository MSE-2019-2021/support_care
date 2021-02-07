import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IToxicityRate } from '../toxicity-rate.model';

export type EntityArrayResponseType = HttpResponse<IToxicityRate[]>;

@Injectable({ providedIn: 'root' })
export class ToxicityRateService {
  public resourceUrl = SERVER_API_URL + 'api/toxicity-rates';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IToxicityRate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
