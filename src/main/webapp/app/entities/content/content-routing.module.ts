import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContentComponent } from './content.component';
import { ContentDetailComponent } from './content-detail.component';
import { ContentUpdateComponent } from './content-update.component';
import { ContentRoutingResolveService } from './content-routing-resolve.service';
import { ContentModule } from './content.module';

const contentRoute: Routes = [
  {
    path: '',
    component: ContentComponent,
    data: {
      authorities: [Authority.VIEWER],
      pageTitle: 'supportivecareApp.content.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContentDetailComponent,
    resolve: {
      content: ContentRoutingResolveService,
    },
    data: {
      authorities: [Authority.VIEWER],
      pageTitle: 'supportivecareApp.content.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContentUpdateComponent,
    resolve: {
      content: ContentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.content.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContentUpdateComponent,
    resolve: {
      content: ContentRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.content.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contentRoute), ContentModule],
  exports: [ContentModule],
})
export class ContentRoutingModule {}
