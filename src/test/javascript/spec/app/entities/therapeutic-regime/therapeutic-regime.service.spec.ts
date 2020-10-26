import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TherapeuticRegimeService } from 'app/entities/therapeutic-regime/therapeutic-regime.service';
import { ITherapeuticRegime, TherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';

describe('Service Tests', () => {
  describe('TherapeuticRegime Service', () => {
    let injector: TestBed;
    let service: TherapeuticRegimeService;
    let httpMock: HttpTestingController;
    let elemDefault: ITherapeuticRegime;
    let expectedResult: ITherapeuticRegime | ITherapeuticRegime[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TherapeuticRegimeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TherapeuticRegime(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
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
            name: 'BBBBBB',
            acronym: 'BBBBBB',
            purpose: 'BBBBBB',
            condition: 'BBBBBB',
            timing: 'BBBBBB',
            indication: 'BBBBBB',
            criteria: 'BBBBBB',
            notice: 'BBBBBB',
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
            name: 'BBBBBB',
            acronym: 'BBBBBB',
            purpose: 'BBBBBB',
            condition: 'BBBBBB',
            timing: 'BBBBBB',
            indication: 'BBBBBB',
            criteria: 'BBBBBB',
            notice: 'BBBBBB',
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
