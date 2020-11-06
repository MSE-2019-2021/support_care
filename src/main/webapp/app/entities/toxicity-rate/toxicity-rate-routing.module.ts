import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ToxicityRateComponent } from './toxicity-rate.component';
import { ToxicityRateDetailComponent } from './toxicity-rate-detail.component';
import { ToxicityRateUpdateComponent } from './toxicity-rate-update.component';
import { ToxicityRateRoutingResolveService } from './toxicity-rate-routing-resolve.service';
import { ToxicityRateModule } from './toxicity-rate.module';

const toxicityRateRoute: Routes = [
  {
    path: '',
    component: ToxicityRateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.toxicityRate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ToxicityRateDetailComponent,
    resolve: {
      toxicityRate: ToxicityRateRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.toxicityRate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ToxicityRateUpdateComponent,
    resolve: {
      toxicityRate: ToxicityRateRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.toxicityRate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ToxicityRateUpdateComponent,
    resolve: {
      toxicityRate: ToxicityRateRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.toxicityRate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(toxicityRateRoute), ToxicityRateModule],
  exports: [ToxicityRateModule],
})
export class ToxicityRateRoutingModule {}
