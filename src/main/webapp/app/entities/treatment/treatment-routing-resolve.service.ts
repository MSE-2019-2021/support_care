import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITreatment, Treatment } from 'app/shared/model/treatment.model';
import { TreatmentService } from './treatment.service';

@Injectable({ providedIn: 'root' })
export class TreatmentRoutingResolveService implements Resolve<ITreatment> {
  constructor(private service: TreatmentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITreatment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((treatment: HttpResponse<Treatment>) => {
          if (treatment.body) {
            return of(treatment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Treatment());
  }
}
