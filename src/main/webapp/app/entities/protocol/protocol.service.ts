import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProtocol } from 'app/shared/model/protocol.model';

type EntityResponseType = HttpResponse<IProtocol>;
type EntityArrayResponseType = HttpResponse<IProtocol[]>;

@Injectable({ providedIn: 'root' })
export class ProtocolService {
  public resourceUrl = SERVER_API_URL + 'api/protocols';

  constructor(protected http: HttpClient) {}

  create(protocol: IProtocol): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(protocol);
    return this.http
      .post<IProtocol>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(protocol: IProtocol): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(protocol);
    return this.http
      .put<IProtocol>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProtocol>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProtocol[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(protocol: IProtocol): IProtocol {
    const copy: IProtocol = Object.assign({}, protocol, {
      createDate: protocol.createDate && protocol.createDate.isValid() ? protocol.createDate.toJSON() : undefined,
      updateDate: protocol.updateDate && protocol.updateDate.isValid() ? protocol.updateDate.toJSON() : undefined,
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
      res.body.forEach((protocol: IProtocol) => {
        protocol.createDate = protocol.createDate ? moment(protocol.createDate) : undefined;
        protocol.updateDate = protocol.updateDate ? moment(protocol.updateDate) : undefined;
      });
    }
    return res;
  }
}
