import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITherapeuticRegime, TherapeuticRegime } from '../therapeutic-regime.model';

import { TherapeuticRegimeService } from './therapeutic-regime.service';
import * as dayjs from 'dayjs';
import { Treatment } from 'app/entities/treatment/treatment.model';

describe('Service Tests', () => {
  describe('TherapeuticRegime Service', () => {
    let service: TherapeuticRegimeService;
    let httpMock: HttpTestingController;
    let elemDefault: ITherapeuticRegime;
    let expectedResult: ITherapeuticRegime | ITherapeuticRegime[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TherapeuticRegimeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = new TherapeuticRegime(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        [],
        new Treatment(),
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

      it('should create a TherapeuticRegime', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate,
            lastModifiedDate: currentDate,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TherapeuticRegime()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TherapeuticRegime', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            acronym: 'BBBBBB',
            purpose: 'BBBBBB',
            condition: 'BBBBBB',
            timing: 'BBBBBB',
            indication: 'BBBBBB',
            criteria: 'BBBBBB',
            notice: 'BBBBBB',
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

      it('should return a list of TherapeuticRegime', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            acronym: 'BBBBBB',
            purpose: 'BBBBBB',
            condition: 'BBBBBB',
            timing: 'BBBBBB',
            indication: 'BBBBBB',
            criteria: 'BBBBBB',
            notice: 'BBBBBB',
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

      it('should delete a TherapeuticRegime', () => {
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
