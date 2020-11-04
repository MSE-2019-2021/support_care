import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITherapeuticRegime, TherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { TherapeuticRegimeService } from './therapeutic-regime.service';

@Injectable({ providedIn: 'root' })
export class TherapeuticRegimeRoutingResolveService implements Resolve<ITherapeuticRegime> {
  constructor(private service: TherapeuticRegimeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITherapeuticRegime> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((therapeuticRegime: HttpResponse<TherapeuticRegime>) => {
          if (therapeuticRegime.body) {
            return of(therapeuticRegime.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TherapeuticRegime());
  }
}
