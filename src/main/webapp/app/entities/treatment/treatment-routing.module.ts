import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TreatmentComponent } from './list/treatment.component';
import { TreatmentDetailComponent } from './detail/treatment-detail.component';
import { TreatmentUpdateComponent } from './update/treatment-update.component';
import { TreatmentRoutingResolveService } from './treatment-routing-resolve.service';
import { TreatmentModule } from './treatment.module';

const treatmentRoute: Routes = [
  {
    path: '',
    component: TreatmentComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TreatmentDetailComponent,
    resolve: {
      treatment: TreatmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TreatmentUpdateComponent,
    resolve: {
      treatment: TreatmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TreatmentUpdateComponent,
    resolve: {
      treatment: TreatmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(treatmentRoute), TreatmentModule],
  exports: [TreatmentModule],
})
export class TreatmentRoutingModule {}
