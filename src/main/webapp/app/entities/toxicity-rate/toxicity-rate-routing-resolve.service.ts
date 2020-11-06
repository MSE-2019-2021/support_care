import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IToxicityRate, ToxicityRate } from 'app/shared/model/toxicity-rate.model';
import { ToxicityRateService } from './toxicity-rate.service';

@Injectable({ providedIn: 'root' })
export class ToxicityRateRoutingResolveService implements Resolve<IToxicityRate> {
  constructor(private service: ToxicityRateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IToxicityRate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((toxicityRate: HttpResponse<ToxicityRate>) => {
          if (toxicityRate.body) {
            return of(toxicityRate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ToxicityRate());
  }
}
