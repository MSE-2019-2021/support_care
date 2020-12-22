jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IContent, Content } from '../content.model';
import { ContentService } from '../service/content.service';

import { ContentRoutingResolveService } from './content-routing-resolve.service';

describe('Service Tests', () => {
  describe('Content routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ContentRoutingResolveService;
    let service: ContentService;
    let resultContent: IContent | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ContentRoutingResolveService);
      service = TestBed.inject(ContentService);
      resultContent = undefined;
    });

    describe('resolve', () => {
      it('should return existing IContent for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new Content(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultContent = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultContent).toEqual(new Content(123));
      });

      it('should return new IContent if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultContent = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultContent).toEqual(new Content());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultContent = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultContent).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
