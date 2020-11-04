import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TherapeuticRegimeComponent } from './therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from './therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from './therapeutic-regime-update.component';
import { TherapeuticRegimeRoutingResolveService } from './therapeutic-regime-routing-resolve.service';
import { TherapeuticRegimeModule } from './therapeutic-regime.module';

const therapeuticRegimeRoute: Routes = [
  {
    path: '',
    component: TherapeuticRegimeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.therapeuticRegime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TherapeuticRegimeDetailComponent,
    resolve: {
      therapeuticRegime: TherapeuticRegimeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.therapeuticRegime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TherapeuticRegimeUpdateComponent,
    resolve: {
      therapeuticRegime: TherapeuticRegimeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.therapeuticRegime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TherapeuticRegimeUpdateComponent,
    resolve: {
      therapeuticRegime: TherapeuticRegimeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.therapeuticRegime.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(therapeuticRegimeRoute), TherapeuticRegimeModule],
  exports: [TherapeuticRegimeModule],
})
export class TherapeuticRegimeRoutingModule {}
