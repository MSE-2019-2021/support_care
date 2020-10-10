import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGuide } from 'app/shared/model/guide.model';

type EntityResponseType = HttpResponse<IGuide>;
type EntityArrayResponseType = HttpResponse<IGuide[]>;

@Injectable({ providedIn: 'root' })
export class GuideService {
  public resourceUrl = SERVER_API_URL + 'api/guides';

  constructor(protected http: HttpClient) {}

  create(guide: IGuide): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guide);
    return this.http
      .post<IGuide>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(guide: IGuide): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guide);
    return this.http
      .put<IGuide>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGuide>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGuide[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(guide: IGuide): IGuide {
    const copy: IGuide = Object.assign({}, guide, {
      createDate: guide.createDate && guide.createDate.isValid() ? guide.createDate.toJSON() : undefined,
      updateDate: guide.updateDate && guide.updateDate.isValid() ? guide.updateDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDate = res.body.createDate ? moment(res.body.createDate) : undefined;
      res.body.updateDate = res.body.updateDate ? moment(res.body.updateDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((guide: IGuide) => {
        guide.createDate = guide.createDate ? moment(guide.createDate) : undefined;
        guide.updateDate = guide.updateDate ? moment(guide.updateDate) : undefined;
      });
    }
    return res;
  }
}
