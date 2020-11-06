import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { ISymptom } from 'app/shared/model/symptom.model';

type EntityResponseType = HttpResponse<ISymptom>;
type EntityArrayResponseType = HttpResponse<ISymptom[]>;

@Injectable({ providedIn: 'root' })
export class SymptomService {
  public resourceUrl = SERVER_API_URL + 'api/symptoms';

  constructor(protected http: HttpClient) {}

  create(symptom: ISymptom): Observable<EntityResponseType> {
    return this.http.post<ISymptom>(this.resourceUrl, symptom, { observe: 'response' });
  }

  update(symptom: ISymptom): Observable<EntityResponseType> {
    return this.http.put<ISymptom>(this.resourceUrl, symptom, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISymptom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISymptom[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
