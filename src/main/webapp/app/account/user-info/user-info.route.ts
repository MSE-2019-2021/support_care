import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserInfoComponent } from './user-info.component';

export const userInfoRoute: Route = {
  path: 'user-info',
  component: UserInfoComponent,
  data: {
    pageTitle: 'global.menu.account.user-info',
  },
  canActivate: [UserRouteAccessService],
};
