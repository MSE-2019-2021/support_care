import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DrugComponent } from './drug.component';
import { DrugDetailComponent } from './drug-detail.component';
import { DrugUpdateComponent } from './drug-update.component';
import { DrugRoutingResolveService } from './drug-routing-resolve.service';
import { DrugModule } from './drug.module';

const drugRoute: Routes = [
  {
    path: '',
    component: DrugComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.drug.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DrugDetailComponent,
    resolve: {
      drug: DrugRoutingResolveService,
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
      drug: DrugRoutingResolveService,
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
      drug: DrugRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.drug.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(drugRoute), DrugModule],
  exports: [DrugModule],
})
export class DrugRoutingModule {}
