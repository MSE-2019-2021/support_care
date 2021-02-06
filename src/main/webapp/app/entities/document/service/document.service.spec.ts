import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DocumentService } from './document.service';

describe('Service Tests', () => {
  describe('Document Service', () => {
    let service: DocumentService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      service = TestBed.inject(DocumentService);
      httpMock = TestBed.inject(HttpTestingController);
    });

    describe('Service methods', () => {
      it('should download a document', () => {
        service.download(123);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
