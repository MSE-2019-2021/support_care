import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';
import { Feedback, IFeedback } from '../feedback.model';

import { FeedbackService } from './feedback.service';
import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

describe('Service Tests', () => {
  describe('Feedback Service', () => {
    let service: FeedbackService;
    let httpMock: HttpTestingController;
    let elemDefault: IFeedback;
    let expectedResult: IFeedback | IFeedback[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FeedbackService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = new Feedback(0, EntityFeedback.ACTIVE_SUBSTANCE, 0, false, 'AAAAAAA', false, false, 'BBBBBBB', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Feedback', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Feedback()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Feedback', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            entityName: 'BBBBBB',
            entityId: 1,
            thumb: true,
            reason: 'BBBBBB',
            solved: true,
            anonym: true,
            createdDate: currentDate.format(DATE_TIME_FORMAT),
            createdBy: 'AAAAAA',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Feedback', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            entityName: 'BBBBBB',
            entityId: 1,
            thumb: true,
            reason: 'BBBBBB',
            solved: true,
            anonym: true,
            createdDate: currentDate.format(DATE_TIME_FORMAT),
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

      it('should delete a Feedback', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      it('should update an entity Feedback', () => {
        const feedback = new Feedback();
        feedback.entityName = EntityFeedback.THERAPEUTIC_REGIME;
        feedback.entityId = 123;

        service.manageFeedbackFromEntity(feedback).subscribe(resp => (expectedResult = resp.ok));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      it('should count thumbs for an entity Feedback', () => {
        const returnedFromService = Object.assign(
          {
            countThumbUp: 1,
            countThumbDown: 0,
            thumb: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.countFeedbacksFromEntity(EntityFeedback.THERAPEUTIC_REGIME, 123).subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
