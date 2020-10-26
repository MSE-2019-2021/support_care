import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';

type EntityResponseType = HttpResponse<ITherapeuticRegime>;
type EntityArrayResponseType = HttpResponse<ITherapeuticRegime[]>;

@Injectable({ providedIn: 'root' })
export class TherapeuticRegimeService {
  public resourceUrl = SERVER_API_URL + 'api/therapeutic-regimes';

  constructor(protected http: HttpClient) {}

  create(therapeuticRegime: ITherapeuticRegime): Observable<EntityResponseType> {
    return this.http.post<ITherapeuticRegime>(this.resourceUrl, therapeuticRegime, { observe: 'response' });
  }

  update(therapeuticRegime: ITherapeuticRegime): Observable<EntityResponseType> {
    return this.http.put<ITherapeuticRegime>(this.resourceUrl, therapeuticRegime, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITherapeuticRegime>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITherapeuticRegime[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
