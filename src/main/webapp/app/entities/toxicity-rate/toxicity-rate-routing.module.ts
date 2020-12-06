import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ToxicityRateComponent } from './list/toxicity-rate.component';
import { ToxicityRateDetailComponent } from './detail/toxicity-rate-detail.component';
import { ToxicityRateUpdateComponent } from './update/toxicity-rate-update.component';
import { ToxicityRateRoutingResolveService } from './toxicity-rate-routing-resolve.service';
import { ToxicityRateModule } from './toxicity-rate.module';

const toxicityRateRoute: Routes = [
  {
    path: '',
    component: ToxicityRateComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ToxicityRateDetailComponent,
    resolve: {
      toxicityRate: ToxicityRateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ToxicityRateUpdateComponent,
    resolve: {
      toxicityRate: ToxicityRateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ToxicityRateUpdateComponent,
    resolve: {
      toxicityRate: ToxicityRateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(toxicityRateRoute), ToxicityRateModule],
  exports: [ToxicityRateModule],
})
export class ToxicityRateRoutingModule {}
