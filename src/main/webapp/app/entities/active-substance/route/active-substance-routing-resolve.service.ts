import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IActiveSubstance, ActiveSubstance } from '../active-substance.model';
import { ActiveSubstanceService } from '../service/active-substance.service';

@Injectable({ providedIn: 'root' })
export class ActiveSubstanceRoutingResolveService implements Resolve<IActiveSubstance> {
  constructor(private service: ActiveSubstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActiveSubstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((activeSubstance: HttpResponse<ActiveSubstance>) => {
          if (activeSubstance.body) {
            return of(activeSubstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActiveSubstance());
  }
}
