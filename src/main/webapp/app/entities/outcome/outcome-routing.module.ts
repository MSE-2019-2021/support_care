import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OutcomeComponent } from './list/outcome.component';
import { OutcomeDetailComponent } from './detail/outcome-detail.component';
import { OutcomeUpdateComponent } from './update/outcome-update.component';
import { OutcomeRoutingResolveService } from './outcome-routing-resolve.service';
import { OutcomeModule } from './outcome.module';

const outcomeRoute: Routes = [
  {
    path: '',
    component: OutcomeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OutcomeDetailComponent,
    resolve: {
      outcome: OutcomeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OutcomeUpdateComponent,
    resolve: {
      outcome: OutcomeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OutcomeUpdateComponent,
    resolve: {
      outcome: OutcomeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(outcomeRoute), OutcomeModule],
  exports: [OutcomeModule],
})
export class OutcomeRoutingModule {}
