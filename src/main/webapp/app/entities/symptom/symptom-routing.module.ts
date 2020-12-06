import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SymptomComponent } from './list/symptom.component';
import { SymptomDetailComponent } from './detail/symptom-detail.component';
import { SymptomUpdateComponent } from './update/symptom-update.component';
import { SymptomRoutingResolveService } from './symptom-routing-resolve.service';
import { SymptomModule } from './symptom.module';

const symptomRoute: Routes = [
  {
    path: '',
    component: SymptomComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SymptomDetailComponent,
    resolve: {
      symptom: SymptomRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SymptomUpdateComponent,
    resolve: {
      symptom: SymptomRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SymptomUpdateComponent,
    resolve: {
      symptom: SymptomRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(symptomRoute), SymptomModule, NgMultiSelectDropDownModule],
  exports: [SymptomModule],
})
export class SymptomRoutingModule {}
