import { TestBed } from '@angular/core/testing';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Authority } from 'app/config/authority.constants';
import { UserService } from 'app/core/user/user.service';
import { IUser, User } from 'app/core/user/user.model';
import { SERVER_API_URL } from 'app/app.constants';

describe('Service Tests', () => {
  describe('User Service', () => {
    let service: UserService;
    let httpMock: HttpTestingController;
    let elemDefault: IUser;
    let date: Date;
    let eResult: IUser | IUser[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });

      service = TestBed.inject(UserService);
      httpMock = TestBed.inject(HttpTestingController);
      eResult = null;
      date = new Date();
      elemDefault = new User(0, 'AAAA', 'AAAA', 'AAAA', 'AAAA', true, 'AAAA', ['AAAA'], 'AAAA', date, 'AAAA', date, 'AAAA');
    });

    afterEach(() => {
      httpMock.verify();
    });

    describe('Service methods', () => {
      it('should call correct URL', () => {
        service.find('user').subscribe();

        const req = httpMock.expectOne({ method: 'GET' });
        const resourceUrl = SERVER_API_URL + 'api/admin/users';
        expect(req.request.url).toEqual(`${resourceUrl}/user`);
      });

      it('should return User', () => {
        let expectedResult: string | undefined;

        service.find('user').subscribe(received => {
          expectedResult = received.login;
        });

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(new User(123, 'user'));
        expect(expectedResult).toEqual('user');
      });

      it('should return Authorities', () => {
        let expectedResult: string[] = [];

        service.authorities().subscribe(authorities => {
          expectedResult = authorities;
        });
        const req = httpMock.expectOne({ method: 'GET' });

        req.flush([Authority.VIEWER, Authority.USER, Authority.ADMIN]);
        expect(expectedResult).toEqual([Authority.VIEWER, Authority.USER, Authority.ADMIN]);
      });

      it('should propagate not found response', () => {
        let expectedResult = 0;

        service.find('user').subscribe({
          error: (error: HttpErrorResponse) => (expectedResult = error.status),
        });

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush('Invalid request parameters', {
          status: 404,
          statusText: 'Bad Request',
        });
        expect(expectedResult).toEqual(404);
      });

      it('should change user password', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            login: 'AAAA',
            firstName: 'AAAA',
            lastName: 'AAAA',
            activated: true,
            langKey: 'AAAA',
            authorities: ['AAAA'],
            createdBy: 'AAAA',
            createdDate: date,
            lastModifiedBy: 'AAAA',
            lastModifiedDate: date,
            password: 'AAAA',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.changePassword(expected).subscribe(resp => (eResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(eResult).toMatchObject(expected);
      });
    });
  });
});
