import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IThumb } from '../thumb.model';
import { IThumbCount } from '../thumb-count.model';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';

export type EntityResponseType = HttpResponse<IThumb>;
export type EntityArrayResponseType = HttpResponse<IThumb[]>;

@Injectable({ providedIn: 'root' })
export class ThumbService {
  public resourceUrl = SERVER_API_URL + 'api/thumbs';

  constructor(protected http: HttpClient) {}

  countThumbsFromEntity(entityType: EntityFeedback, entityId: number): Observable<EntityResponseType> {
    return this.http.get<IThumbCount>(`${this.resourceUrl}/${entityType.valueOf()}/${entityId}/count`, { observe: 'response' });
  }

  manageThumbFromEntity(thumb: IThumb): Observable<HttpResponse<{}>> {
    return this.http.post(`${this.resourceUrl}/${thumb.entityType!.valueOf()}/${thumb.entityId!}`, thumb, { observe: 'response' });
  }
}
