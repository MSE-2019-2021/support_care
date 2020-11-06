import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DocumentComponent } from './document.component';
import { DocumentDetailComponent } from './document-detail.component';
import { DocumentUpdateComponent } from './document-update.component';
import { DocumentRoutingResolveService } from './document-routing-resolve.service';
import { DocumentModule } from './document.module';

const documentRoute: Routes = [
  {
    path: '',
    component: DocumentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.document.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentDetailComponent,
    resolve: {
      document: DocumentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.document.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentUpdateComponent,
    resolve: {
      document: DocumentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.document.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentUpdateComponent,
    resolve: {
      document: DocumentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.document.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(documentRoute), DocumentModule],
  exports: [DocumentModule],
})
export class DocumentRoutingModule {}
