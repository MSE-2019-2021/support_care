jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISymptom, Symptom } from '../symptom.model';
import { SymptomService } from '../service/symptom.service';

import { SymptomRoutingResolveService } from './symptom-routing-resolve.service';

describe('Service Tests', () => {
  describe('Symptom routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: SymptomRoutingResolveService;
    let service: SymptomService;
    let resultSymptom: ISymptom | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(SymptomRoutingResolveService);
      service = TestBed.inject(SymptomService);
      resultSymptom = undefined;
    });

    describe('resolve', () => {
      it('should return existing ISymptom for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new Symptom(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSymptom = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSymptom).toEqual(new Symptom(123));
      });

      it('should return new ISymptom if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSymptom = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultSymptom).toEqual(new Symptom());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSymptom = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSymptom).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
