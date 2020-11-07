import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SymptomComponent } from './symptom.component';
import { SymptomDetailComponent } from './symptom-detail.component';
import { SymptomUpdateComponent } from './symptom-update.component';
import { SymptomRoutingResolveService } from './symptom-routing-resolve.service';
import { SymptomModule } from './symptom.module';

const symptomRoute: Routes = [
  {
    path: '',
    component: SymptomComponent,
    data: {
      authorities: [Authority.VIEWER],
      pageTitle: 'supportivecareApp.symptom.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SymptomDetailComponent,
    resolve: {
      symptom: SymptomRoutingResolveService,
    },
    data: {
      authorities: [Authority.VIEWER],
      pageTitle: 'supportivecareApp.symptom.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SymptomUpdateComponent,
    resolve: {
      symptom: SymptomRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.symptom.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SymptomUpdateComponent,
    resolve: {
      symptom: SymptomRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.symptom.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(symptomRoute), SymptomModule],
  exports: [SymptomModule],
})
export class SymptomRoutingModule {}
