import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IActiveSubstance, ActiveSubstance } from '../active-substance.model';

import { ActiveSubstanceService } from './active-substance.service';
import * as dayjs from 'dayjs';
import { Administration } from 'app/entities/administration/administration.model';

describe('Service Tests', () => {
  describe('ActiveSubstance Service', () => {
    let service: ActiveSubstanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IActiveSubstance;
    let expectedResult: IActiveSubstance | IActiveSubstance[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ActiveSubstanceService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = new ActiveSubstance(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        [],
        new Administration(),
        [],
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ActiveSubstance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate,
            lastModifiedDate: currentDate,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ActiveSubstance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ActiveSubstance', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            dosage: 'BBBBBB',
            form: 'BBBBBB',
            description: 'BBBBBB',
            createdDate: currentDate,
            lastModifiedDate: currentDate,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ActiveSubstance', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            dosage: 'BBBBBB',
            form: 'BBBBBB',
            description: 'BBBBBB',
            createdDate: currentDate,
            lastModifiedDate: currentDate,
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

      it('should delete a ActiveSubstance', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
