import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IThumb } from '../thumb.model';
import { IThumbCount } from '../thumb-count.model';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';

export type EntityResponseType = HttpResponse<IThumb>;
export type EntityArrayResponseType = HttpResponse<IThumb[]>;

@Injectable({ providedIn: 'root' })
export class ThumbService {
  public resourceUrl = SERVER_API_URL + 'api/thumbs';

  constructor(protected http: HttpClient) {}

  create(thumb: IThumb): Observable<EntityResponseType> {
    return this.http.post<IThumb>(this.resourceUrl, thumb, { observe: 'response' });
  }

  update(thumb: IThumb): Observable<EntityResponseType> {
    return this.http.put<IThumb>(this.resourceUrl, thumb, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IThumb>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IThumb[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  countThumbsFromEntity(entityType: EntityFeedback, entityId: number): Observable<EntityResponseType> {
    return this.http.get<IThumbCount>(`${this.resourceUrl}/${entityType.valueOf()}/${entityId}/count`, { observe: 'response' });
  }

  manageThumbFromEntity(thumb: IThumb): Observable<HttpResponse<{}>> {
    return this.http.post(`${this.resourceUrl}/${thumb.entityType!.valueOf()}/${thumb.entityId!}`, thumb, { observe: 'response' });
  }
}
