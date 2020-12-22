import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TherapeuticRegimeComponent } from '../list/therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from '../detail/therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from '../update/therapeutic-regime-update.component';
import { TherapeuticRegimeRoutingResolveService } from './therapeutic-regime-routing-resolve.service';

const therapeuticRegimeRoute: Routes = [
  {
    path: '',
    component: TherapeuticRegimeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TherapeuticRegimeDetailComponent,
    resolve: {
      therapeuticRegime: TherapeuticRegimeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TherapeuticRegimeUpdateComponent,
    resolve: {
      therapeuticRegime: TherapeuticRegimeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TherapeuticRegimeUpdateComponent,
    resolve: {
      therapeuticRegime: TherapeuticRegimeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(therapeuticRegimeRoute)],
  exports: [RouterModule],
})
export class TherapeuticRegimeRoutingModule {}
