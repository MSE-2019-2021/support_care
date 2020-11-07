import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Authority } from 'app/core/user/authority.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NoticeComponent } from './notice.component';
import { NoticeDetailComponent } from './notice-detail.component';
import { NoticeUpdateComponent } from './notice-update.component';
import { NoticeRoutingResolveService } from './notice-routing-resolve.service';
import { NoticeModule } from './notice.module';

const noticeRoute: Routes = [
  {
    path: '',
    component: NoticeComponent,
    data: {
      authorities: [Authority.VIEWER],
      pageTitle: 'supportivecareApp.notice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NoticeDetailComponent,
    resolve: {
      notice: NoticeRoutingResolveService,
    },
    data: {
      authorities: [Authority.VIEWER],
      pageTitle: 'supportivecareApp.notice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NoticeUpdateComponent,
    resolve: {
      notice: NoticeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.notice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NoticeUpdateComponent,
    resolve: {
      notice: NoticeRoutingResolveService,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'supportivecareApp.notice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(noticeRoute), NoticeModule],
  exports: [NoticeModule],
})
export class NoticeRoutingModule {}
