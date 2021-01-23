import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOutcome, Outcome } from '../outcome.model';

import { OutcomeService } from './outcome.service';
import * as dayjs from 'dayjs';

interface MockFile {
  name: string;
  body: string;
  mimeType: string;
}

const createFileFromMockFile = (file: MockFile): File => {
  const blob = new Blob([file.body], { type: file.mimeType }) as any;
  blob['lastModifiedDate'] = new Date();
  blob['name'] = file.name;
  return blob as File;
};

const createMockFileList = (files: MockFile[]): FileList => {
  const fileList: FileList = {
    length: files.length,
    item(index: number): File {
      return fileList[index];
    },
  };
  files.forEach((file, index) => (fileList[index] = createFileFromMockFile(file)));

  return fileList;
};

describe('Service Tests', () => {
  describe('Outcome Service', () => {
    let service: OutcomeService;
    let httpMock: HttpTestingController;
    let elemDefault: IOutcome;
    let expectedResult: IOutcome | IOutcome[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(OutcomeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = new Outcome(0, 'AAAAAAA', 'AAAAAAA', 'DDDDDDD', [], currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Outcome with no files', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate,
            lastModifiedDate: currentDate,
          },
          elemDefault
        );

        const files = {} as FileList;

        const expected = Object.assign({}, returnedFromService);

        service.create(new Outcome(), files).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should create a Outcome with files', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate,
            lastModifiedDate: currentDate,
          },
          elemDefault
        );

        const fileList = createMockFileList([
          {
            body: 'test',
            mimeType: 'text/plain',
            name: 'test.txt',
          },
        ]);

        const expected = Object.assign({}, returnedFromService);

        service.create(new Outcome(), fileList).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Outcome with no files', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            createdDate: currentDate,
            lastModifiedDate: currentDate,
          },
          elemDefault
        );

        const files = {} as FileList;

        const expected = Object.assign({}, returnedFromService);

        service.update(expected, files).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Outcome with no files', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            createdDate: currentDate,
            lastModifiedDate: currentDate,
          },
          elemDefault
        );

        const fileList = createMockFileList([
          {
            body: 'test',
            mimeType: 'text/plain',
            name: 'test.txt',
          },
        ]);

        const expected = Object.assign({}, returnedFromService);

        service.update(expected, fileList).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Outcome', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
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

      it('should delete a Outcome', () => {
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
