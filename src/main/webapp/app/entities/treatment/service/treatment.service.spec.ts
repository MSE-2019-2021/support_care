import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITreatment, Treatment } from '../treatment.model';

import { TreatmentService } from './treatment.service';

describe('Service Tests', () => {
  describe('Treatment Service', () => {
    let service: TreatmentService;
    let httpMock: HttpTestingController;
    let elemDefault: ITreatment;
    let expectedResult: ITreatment | ITreatment[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TreatmentService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = new Treatment(0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should return a list of Treatment', () => {
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
