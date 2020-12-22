import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IActiveSubstance } from '../active-substance.model';

type EntityResponseType = HttpResponse<IActiveSubstance>;
type EntityArrayResponseType = HttpResponse<IActiveSubstance[]>;

@Injectable({ providedIn: 'root' })
export class ActiveSubstanceService {
  public resourceUrl = SERVER_API_URL + 'api/active-substances';

  constructor(protected http: HttpClient) {}

  create(activeSubstance: IActiveSubstance): Observable<EntityResponseType> {
    return this.http.post<IActiveSubstance>(this.resourceUrl, activeSubstance, { observe: 'response' });
  }

  update(activeSubstance: IActiveSubstance): Observable<EntityResponseType> {
    return this.http.put<IActiveSubstance>(this.resourceUrl, activeSubstance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActiveSubstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActiveSubstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
