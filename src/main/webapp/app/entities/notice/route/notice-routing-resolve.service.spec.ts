jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INotice, Notice } from '../notice.model';
import { NoticeService } from '../service/notice.service';

import { NoticeRoutingResolveService } from './notice-routing-resolve.service';

describe('Service Tests', () => {
  describe('Notice routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: NoticeRoutingResolveService;
    let service: NoticeService;
    let resultNotice: INotice | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(NoticeRoutingResolveService);
      service = TestBed.inject(NoticeService);
      resultNotice = undefined;
    });

    describe('resolve', () => {
      it('should return existing INotice for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new Notice(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNotice = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNotice).toEqual(new Notice(123));
      });

      it('should return new INotice if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNotice = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultNotice).toEqual(new Notice());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNotice = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNotice).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
