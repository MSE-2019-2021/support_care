jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITherapeuticRegime, TherapeuticRegime } from '../therapeutic-regime.model';
import { TherapeuticRegimeService } from '../service/therapeutic-regime.service';

import { TherapeuticRegimeRoutingResolveService } from './therapeutic-regime-routing-resolve.service';

describe('Service Tests', () => {
  describe('TherapeuticRegime routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TherapeuticRegimeRoutingResolveService;
    let service: TherapeuticRegimeService;
    let resultTherapeuticRegime: ITherapeuticRegime | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TherapeuticRegimeRoutingResolveService);
      service = TestBed.inject(TherapeuticRegimeService);
      resultTherapeuticRegime = undefined;
    });

    describe('resolve', () => {
      it('should return existing ITherapeuticRegime for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new TherapeuticRegime(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTherapeuticRegime = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTherapeuticRegime).toEqual(new TherapeuticRegime(123));
      });

      it('should return new ITherapeuticRegime if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTherapeuticRegime = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTherapeuticRegime).toEqual(new TherapeuticRegime());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTherapeuticRegime = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTherapeuticRegime).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
