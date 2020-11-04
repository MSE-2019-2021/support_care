import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDrug, Drug } from 'app/shared/model/drug.model';
import { DrugService } from './drug.service';

@Injectable({ providedIn: 'root' })
export class DrugRoutingResolveService implements Resolve<IDrug> {
  constructor(private service: DrugService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDrug> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((drug: HttpResponse<Drug>) => {
          if (drug.body) {
            return of(drug.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Drug());
  }
}
