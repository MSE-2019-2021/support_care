import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserInfoComponent } from './user-info.component';
import { Authority } from 'app/core/user/authority.model';

export const userInfoRoute: Route = {
  path: 'user-info',
  component: UserInfoComponent,
  data: {
    authorities: [Authority.VIEWER],
    pageTitle: 'global.menu.account.user-info',
  },
  canActivate: [UserRouteAccessService],
};
