import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdministration } from '../administration.model';

export type EntityResponseType = HttpResponse<IAdministration>;
export type EntityArrayResponseType = HttpResponse<IAdministration[]>;

@Injectable({ providedIn: 'root' })
export class AdministrationService {
  public resourceUrl = SERVER_API_URL + 'api/administrations';

  constructor(protected http: HttpClient) {}

  create(administration: IAdministration): Observable<EntityResponseType> {
    return this.http.post<IAdministration>(this.resourceUrl, administration, { observe: 'response' });
  }

  update(administration: IAdministration): Observable<EntityResponseType> {
    return this.http.put<IAdministration>(this.resourceUrl, administration, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdministration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdministration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
