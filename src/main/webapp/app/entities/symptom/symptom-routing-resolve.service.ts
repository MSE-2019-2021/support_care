import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISymptom, Symptom } from 'app/shared/model/symptom.model';
import { SymptomService } from './symptom.service';

@Injectable({ providedIn: 'root' })
export class SymptomRoutingResolveService implements Resolve<ISymptom> {
  constructor(private service: SymptomService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISymptom> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((symptom: HttpResponse<Symptom>) => {
          if (symptom.body) {
            return of(symptom.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Symptom());
  }
}
