import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IFeedback } from '../feedback.model';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

export type EntityResponseType = HttpResponse<IFeedback>;
export type EntityArrayResponseType = HttpResponse<IFeedback[]>;

@Injectable({ providedIn: 'root' })
export class FeedbackService {
  public resourceUrl = SERVER_API_URL + 'api/feedbacks';

  constructor(protected http: HttpClient) {}

  create(feedback: IFeedback): Observable<EntityResponseType> {
    return this.http
      .post<IFeedback>(this.resourceUrl, feedback, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(feedback: IFeedback): Observable<EntityResponseType> {
    return this.http
      .put<IFeedback>(this.resourceUrl, feedback, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFeedback>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFeedback[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((feedback: IFeedback) => {
        feedback.createdDate = feedback.createdDate ? dayjs(feedback.createdDate) : undefined;
      });
    }
    return res;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
    }
    return res;
  }
}
