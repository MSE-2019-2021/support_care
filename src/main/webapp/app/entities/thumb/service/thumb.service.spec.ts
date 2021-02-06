import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';
import { IThumb, Thumb } from '../thumb.model';

import { ThumbService } from './thumb.service';

describe('Service Tests', () => {
  describe('Thumb Service', () => {
    let service: ThumbService;
    let httpMock: HttpTestingController;
    let expectedResult: IThumb | IThumb[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ThumbService);
      httpMock = TestBed.inject(HttpTestingController);
    });

    describe('Service methods', () => {
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
        const returnedFromService = Object.assign({
          countThumbUp: 1,
          countThumbDown: 0,
          thumb: true,
        });

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
