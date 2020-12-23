jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IToxicityRate, ToxicityRate } from '../toxicity-rate.model';
import { ToxicityRateService } from '../service/toxicity-rate.service';

import { ToxicityRateRoutingResolveService } from './toxicity-rate-routing-resolve.service';

describe('Service Tests', () => {
  describe('ToxicityRate routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ToxicityRateRoutingResolveService;
    let service: ToxicityRateService;
    let resultToxicityRate: IToxicityRate | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ToxicityRateRoutingResolveService);
      service = TestBed.inject(ToxicityRateService);
      resultToxicityRate = undefined;
    });

    describe('resolve', () => {
      it('should return existing IToxicityRate for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new ToxicityRate(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultToxicityRate = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultToxicityRate).toEqual(new ToxicityRate(123));
      });

      it('should return new IToxicityRate if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultToxicityRate = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultToxicityRate).toEqual(new ToxicityRate());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultToxicityRate = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultToxicityRate).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
