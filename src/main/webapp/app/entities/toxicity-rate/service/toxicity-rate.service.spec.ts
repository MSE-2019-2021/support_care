import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IToxicityRate, ToxicityRate } from '../toxicity-rate.model';

import { ToxicityRateService } from './toxicity-rate.service';

describe('Service Tests', () => {
  describe('ToxicityRate Service', () => {
    let service: ToxicityRateService;
    let httpMock: HttpTestingController;
    let elemDefault: IToxicityRate;
    let expectedResult: IToxicityRate | IToxicityRate[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ToxicityRateService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = new ToxicityRate(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should return a list of ToxicityRate', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            notice: 'BBBBBB',
            autonomousIntervention: 'BBBBBB',
            interdependentIntervention: 'BBBBBB',
            selfManagement: 'BBBBBB',
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
