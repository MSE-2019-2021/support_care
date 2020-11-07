import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdministrationComponent } from './administration.component';
import { AdministrationDetailComponent } from './administration-detail.component';
import { AdministrationUpdateComponent } from './administration-update.component';
import { AdministrationRoutingResolveService } from './administration-routing-resolve.service';
import { AdministrationModule } from './administration.module';

const administrationRoute: Routes = [
  {
    path: '',
    component: AdministrationComponent,
    data: {
      authorities: [Authority.VIEWER],
      pageTitle: 'supportivecareApp.administration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdministrationDetailComponent,
    resolve: {
      administration: AdministrationRoutingResolveService,
    },
    data: {
      authorities: [Authority.VIEWER],
      pageTitle: 'supportivecareApp.administration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdministrationUpdateComponent,
    resolve: {
      administration: AdministrationRoutingResolveService,
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
      administration: AdministrationRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.administration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(administrationRoute), AdministrationModule],
  exports: [AdministrationModule],
})
export class AdministrationRoutingModule {}
