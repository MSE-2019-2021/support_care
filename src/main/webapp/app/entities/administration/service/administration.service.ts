import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdministration } from '../administration.model';

export type EntityArrayResponseType = HttpResponse<IAdministration[]>;

@Injectable({ providedIn: 'root' })
export class AdministrationService {
  public resourceUrl = SERVER_API_URL + 'api/administrations';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdministration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
