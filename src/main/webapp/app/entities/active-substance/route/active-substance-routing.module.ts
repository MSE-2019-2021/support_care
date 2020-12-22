import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ActiveSubstanceComponent } from '../list/active-substance.component';
import { ActiveSubstanceDetailComponent } from '../detail/active-substance-detail.component';
import { ActiveSubstanceUpdateComponent } from '../update/active-substance-update.component';
import { ActiveSubstanceRoutingResolveService } from './active-substance-routing-resolve.service';

const activeSubstanceRoute: Routes = [
  {
    path: '',
    component: ActiveSubstanceComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActiveSubstanceDetailComponent,
    resolve: {
      activeSubstance: ActiveSubstanceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActiveSubstanceUpdateComponent,
    resolve: {
      activeSubstance: ActiveSubstanceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActiveSubstanceUpdateComponent,
    resolve: {
      activeSubstance: ActiveSubstanceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(activeSubstanceRoute)],
  exports: [RouterModule],
})
export class ActiveSubstanceRoutingModule {}
