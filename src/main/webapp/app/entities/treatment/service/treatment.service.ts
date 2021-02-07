import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { ITreatment } from '../treatment.model';

export type EntityResponseType = HttpResponse<ITreatment>;
export type EntityArrayResponseType = HttpResponse<ITreatment[]>;

@Injectable({ providedIn: 'root' })
export class TreatmentService {
  public resourceUrl = SERVER_API_URL + 'api/treatments';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITreatment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
