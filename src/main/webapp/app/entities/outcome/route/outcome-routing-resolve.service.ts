import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOutcome, Outcome } from '../outcome.model';
import { OutcomeService } from '../service/outcome.service';

@Injectable({ providedIn: 'root' })
export class OutcomeRoutingResolveService implements Resolve<IOutcome> {
  constructor(private service: OutcomeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOutcome> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((outcome: HttpResponse<Outcome>) => {
          if (outcome.body) {
            return of(outcome.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Outcome());
  }
}
