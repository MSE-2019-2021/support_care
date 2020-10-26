import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDrug, Drug } from 'app/shared/model/drug.model';
import { DrugService } from './drug.service';
import { DrugComponent } from './drug.component';
import { DrugDetailComponent } from './drug-detail.component';
import { DrugUpdateComponent } from './drug-update.component';

@Injectable({ providedIn: 'root' })
export class DrugResolve implements Resolve<IDrug> {
  constructor(private service: DrugService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDrug> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((drug: HttpResponse<Drug>) => {
          if (drug.body) {
            return of(drug.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Drug());
  }
}

export const drugRoute: Routes = [
  {
    path: '',
    component: DrugComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'supportivecareApp.drug.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DrugDetailComponent,
    resolve: {
      drug: DrugResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.drug.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DrugUpdateComponent,
    resolve: {
      drug: DrugResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.drug.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DrugUpdateComponent,
    resolve: {
      drug: DrugResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.drug.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
