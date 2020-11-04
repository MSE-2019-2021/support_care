import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TreatmentComponent } from './treatment.component';
import { TreatmentDetailComponent } from './treatment-detail.component';
import { TreatmentUpdateComponent } from './treatment-update.component';
import { TreatmentRoutingResolveService } from './treatment-routing-resolve.service';
import { TreatmentModule } from './treatment.module';

const treatmentRoute: Routes = [
  {
    path: '',
    component: TreatmentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.treatment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TreatmentDetailComponent,
    resolve: {
      treatment: TreatmentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.treatment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TreatmentUpdateComponent,
    resolve: {
      treatment: TreatmentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.treatment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TreatmentUpdateComponent,
    resolve: {
      treatment: TreatmentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.treatment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(treatmentRoute), TreatmentModule],
  exports: [TreatmentModule],
})
export class TreatmentRoutingModule {}
