import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OutcomeComponent } from './outcome.component';
import { OutcomeDetailComponent } from './outcome-detail.component';
import { OutcomeUpdateComponent } from './outcome-update.component';
import { OutcomeRoutingResolveService } from './outcome-routing-resolve.service';
import { OutcomeModule } from './outcome.module';

const outcomeRoute: Routes = [
  {
    path: '',
    component: OutcomeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.outcome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OutcomeDetailComponent,
    resolve: {
      outcome: OutcomeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.outcome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OutcomeUpdateComponent,
    resolve: {
      outcome: OutcomeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.outcome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OutcomeUpdateComponent,
    resolve: {
      outcome: OutcomeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.outcome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(outcomeRoute), OutcomeModule],
  exports: [OutcomeModule],
})
export class OutcomeRoutingModule {}
