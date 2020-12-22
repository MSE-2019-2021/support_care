jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAdministration, Administration } from '../administration.model';
import { AdministrationService } from '../service/administration.service';

import { AdministrationRoutingResolveService } from './administration-routing-resolve.service';

describe('Service Tests', () => {
  describe('Administration routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AdministrationRoutingResolveService;
    let service: AdministrationService;
    let resultAdministration: IAdministration | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AdministrationRoutingResolveService);
      service = TestBed.inject(AdministrationService);
      resultAdministration = undefined;
    });

    describe('resolve', () => {
      it('should return existing IAdministration for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new Administration(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAdministration = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAdministration).toEqual(new Administration(123));
      });

      it('should return new IAdministration if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAdministration = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAdministration).toEqual(new Administration());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAdministration = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAdministration).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
