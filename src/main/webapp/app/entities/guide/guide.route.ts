import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGuide, Guide } from 'app/shared/model/guide.model';
import { GuideService } from './guide.service';
import { GuideComponent } from './guide.component';
import { GuideDetailComponent } from './guide-detail.component';
import { GuideUpdateComponent } from './guide-update.component';

@Injectable({ providedIn: 'root' })
export class GuideResolve implements Resolve<IGuide> {
  constructor(private service: GuideService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGuide> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((guide: HttpResponse<Guide>) => {
          if (guide.body) {
            return of(guide.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Guide());
  }
}

export const guideRoute: Routes = [
  {
    path: '',
    component: GuideComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'supportcareApp.guide.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GuideDetailComponent,
    resolve: {
      guide: GuideResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportcareApp.guide.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GuideUpdateComponent,
    resolve: {
      guide: GuideResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportcareApp.guide.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GuideUpdateComponent,
    resolve: {
      guide: GuideResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportcareApp.guide.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
