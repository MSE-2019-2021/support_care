import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';
import { IThumb, Thumb } from '../thumb.model';

import { ThumbService } from './thumb.service';

describe('Service Tests', () => {
  describe('Thumb Service', () => {
    let service: ThumbService;
    let httpMock: HttpTestingController;
    let elemDefault: IThumb;
    let expectedResult: IThumb | IThumb[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ThumbService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = new Thumb(0, EntityFeedback.ACTIVE_SUBSTANCE, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Thumb', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Thumb()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Thumb', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            entityType: 'BBBBBB',
            entityId: 1,
            thumb: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Thumb', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            entityType: 'BBBBBB',
            entityId: 1,
            thumb: true,
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

      it('should delete a Thumb', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      it('should update an entity Thumb', () => {
        const thumb = new Thumb();
        thumb.entityType = EntityFeedback.THERAPEUTIC_REGIME;
        thumb.entityId = 123;

        service.manageThumbFromEntity(thumb).subscribe(resp => (expectedResult = resp.ok));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      it('should count thumbs for an entity Thumb', () => {
        const returnedFromService = Object.assign(
          {
            countThumbUp: 1,
            countThumbDown: 0,
            thumb: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.countThumbsFromEntity(EntityFeedback.THERAPEUTIC_REGIME, 123).subscribe(resp => (expectedResult = resp.body));
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
