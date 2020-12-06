import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ContentComponent } from './list/content.component';
import { ContentDetailComponent } from './detail/content-detail.component';
import { ContentUpdateComponent } from './update/content-update.component';
import { ContentRoutingResolveService } from './content-routing-resolve.service';
import { ContentModule } from './content.module';

const contentRoute: Routes = [
  {
    path: '',
    component: ContentComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContentDetailComponent,
    resolve: {
      content: ContentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContentUpdateComponent,
    resolve: {
      content: ContentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContentUpdateComponent,
    resolve: {
      content: ContentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(contentRoute), ContentModule],
  exports: [ContentModule],
})
export class ContentRoutingModule {}
