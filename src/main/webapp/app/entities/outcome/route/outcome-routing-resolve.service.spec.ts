jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOutcome, Outcome } from '../outcome.model';
import { OutcomeService } from '../service/outcome.service';

import { OutcomeRoutingResolveService } from './outcome-routing-resolve.service';

describe('Service Tests', () => {
  describe('Outcome routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: OutcomeRoutingResolveService;
    let service: OutcomeService;
    let resultOutcome: IOutcome | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(OutcomeRoutingResolveService);
      service = TestBed.inject(OutcomeService);
      resultOutcome = undefined;
    });

    describe('resolve', () => {
      it('should return existing IOutcome for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new Outcome(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOutcome = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOutcome).toEqual(new Outcome(123));
      });

      it('should return new IOutcome if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOutcome = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultOutcome).toEqual(new Outcome());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOutcome = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOutcome).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
