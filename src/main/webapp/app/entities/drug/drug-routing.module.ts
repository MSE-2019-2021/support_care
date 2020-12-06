import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DrugComponent } from './list/drug.component';
import { DrugDetailComponent } from './detail/drug-detail.component';
import { DrugUpdateComponent } from './update/drug-update.component';
import { DrugRoutingResolveService } from './drug-routing-resolve.service';
import { DrugModule } from './drug.module';

const drugRoute: Routes = [
  {
    path: '',
    component: DrugComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DrugDetailComponent,
    resolve: {
      drug: DrugRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DrugUpdateComponent,
    resolve: {
      drug: DrugRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DrugUpdateComponent,
    resolve: {
      drug: DrugRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(drugRoute), DrugModule],
  exports: [DrugModule],
})
export class DrugRoutingModule {}
