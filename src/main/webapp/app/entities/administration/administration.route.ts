import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdministration, Administration } from 'app/shared/model/administration.model';
import { AdministrationService } from './administration.service';
import { AdministrationComponent } from './administration.component';
import { AdministrationDetailComponent } from './administration-detail.component';
import { AdministrationUpdateComponent } from './administration-update.component';

@Injectable({ providedIn: 'root' })
export class AdministrationResolve implements Resolve<IAdministration> {
  constructor(private service: AdministrationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdministration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((administration: HttpResponse<Administration>) => {
          if (administration.body) {
            return of(administration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Administration());
  }
}

export const administrationRoute: Routes = [
  {
    path: '',
    component: AdministrationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.administration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdministrationDetailComponent,
    resolve: {
      administration: AdministrationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.administration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdministrationUpdateComponent,
    resolve: {
      administration: AdministrationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.administration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdministrationUpdateComponent,
    resolve: {
      administration: AdministrationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.administration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
