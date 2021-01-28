import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FeedbackComponent } from '../list/feedback.component';

const feedbackRoute: Routes = [
  {
    path: '',
    component: FeedbackComponent,
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(feedbackRoute)],
  exports: [RouterModule],
})
export class FeedbackRoutingModule {}
