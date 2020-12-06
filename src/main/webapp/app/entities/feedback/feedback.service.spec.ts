import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { IFeedback, Feedback } from 'app/shared/model/feedback.model';
import { EntityFeedback } from 'app/shared/model/enumerations/entity-feedback.model';

import { FeedbackService } from './feedback.service';

describe('Service Tests', () => {
  describe('Feedback Service', () => {
    let service: FeedbackService;
    let httpMock: HttpTestingController;
    let elemDefault: IFeedback;
    let expectedResult: IFeedback | IFeedback[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FeedbackService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = new Feedback(0, EntityFeedback.DRUG, 0, false, 'AAAAAAA', false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Feedback', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
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
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
