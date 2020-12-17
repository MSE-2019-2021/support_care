import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdministrationComponent } from '../list/administration.component';
import { AdministrationDetailComponent } from '../detail/administration-detail.component';
import { AdministrationUpdateComponent } from '../update/administration-update.component';
import { AdministrationRoutingResolveService } from './administration-routing-resolve.service';
import { AdministrationModule } from '../administration.module';

const administrationRoute: Routes = [
  {
    path: '',
    component: AdministrationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdministrationDetailComponent,
    resolve: {
      administration: AdministrationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdministrationUpdateComponent,
    resolve: {
      administration: AdministrationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdministrationUpdateComponent,
    resolve: {
      administration: AdministrationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(administrationRoute), AdministrationModule],
})
export class AdministrationRoutingModule {}
