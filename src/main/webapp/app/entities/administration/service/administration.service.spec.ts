import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdministration, Administration } from '../administration.model';

import { AdministrationService } from './administration.service';

describe('Service Tests', () => {
  describe('Administration Service', () => {
    let service: AdministrationService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdministration;
    let expectedResult: IAdministration | IAdministration[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AdministrationService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = new Administration(0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should return a list of Administration', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            type: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
