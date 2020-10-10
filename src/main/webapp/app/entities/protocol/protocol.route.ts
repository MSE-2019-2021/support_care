import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProtocol, Protocol } from 'app/shared/model/protocol.model';
import { ProtocolService } from './protocol.service';
import { ProtocolComponent } from './protocol.component';
import { ProtocolDetailComponent } from './protocol-detail.component';
import { ProtocolUpdateComponent } from './protocol-update.component';

@Injectable({ providedIn: 'root' })
export class ProtocolResolve implements Resolve<IProtocol> {
  constructor(private service: ProtocolService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProtocol> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((protocol: HttpResponse<Protocol>) => {
          if (protocol.body) {
            return of(protocol.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Protocol());
  }
}

export const protocolRoute: Routes = [
  {
    path: '',
    component: ProtocolComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'supportcareApp.protocol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProtocolDetailComponent,
    resolve: {
      protocol: ProtocolResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportcareApp.protocol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProtocolUpdateComponent,
    resolve: {
      protocol: ProtocolResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportcareApp.protocol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProtocolUpdateComponent,
    resolve: {
      protocol: ProtocolResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportcareApp.protocol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
