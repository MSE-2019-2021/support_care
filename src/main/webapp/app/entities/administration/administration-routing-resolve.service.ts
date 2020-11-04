import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdministration, Administration } from 'app/shared/model/administration.model';
import { AdministrationService } from './administration.service';

@Injectable({ providedIn: 'root' })
export class AdministrationRoutingResolveService implements Resolve<IAdministration> {
  constructor(private service: AdministrationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdministration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((administration: HttpResponse<Administration>) => {
          if (administration.body) {
            return of(administration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Administration());
  }
}
