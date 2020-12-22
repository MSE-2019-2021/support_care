jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IActiveSubstance, ActiveSubstance } from '../active-substance.model';
import { ActiveSubstanceService } from '../service/active-substance.service';

import { ActiveSubstanceRoutingResolveService } from './active-substance-routing-resolve.service';

describe('Service Tests', () => {
  describe('ActiveSubstance routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ActiveSubstanceRoutingResolveService;
    let service: ActiveSubstanceService;
    let resultActiveSubstance: IActiveSubstance | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ActiveSubstanceRoutingResolveService);
      service = TestBed.inject(ActiveSubstanceService);
      resultActiveSubstance = undefined;
    });

    describe('resolve', () => {
      it('should return existing IActiveSubstance for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new ActiveSubstance(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultActiveSubstance = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultActiveSubstance).toEqual(new ActiveSubstance(123));
      });

      it('should return new IActiveSubstance if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultActiveSubstance = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultActiveSubstance).toEqual(new ActiveSubstance());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultActiveSubstance = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultActiveSubstance).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
