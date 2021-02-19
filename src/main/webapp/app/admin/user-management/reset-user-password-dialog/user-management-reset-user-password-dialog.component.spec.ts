import { UserManagementDeleteDialogComponent } from 'app/admin/user-management/delete/user-management-delete-dialog.component';

jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, waitForAsync, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { UserService } from 'app/core/user/user.service';

import { UserManagementResetUserPasswordDialogComponent } from './user-management-reset-user-password-dialog.component';
import { User } from 'app/core/user/user.model';

describe('Component Tests', () => {
  describe('User Management Reset User Password Component', () => {
    let comp: UserManagementResetUserPasswordDialogComponent;
    let fixture: ComponentFixture<UserManagementResetUserPasswordDialogComponent>;
    let service: UserService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          declarations: [UserManagementResetUserPasswordDialogComponent],
          providers: [NgbActiveModal],
        })
          .overrideTemplate(UserManagementResetUserPasswordDialogComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(UserManagementResetUserPasswordDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(UserService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmReset', () => {
      it('Should call changePassword service on confirmReset', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          const entity = new User(123);
          spyOn(service, 'changePassword').and.returnValue(of({}));

          // WHEN
          comp.confirmReset(entity);
          tick();

          // THEN
          expect(service.changePassword).toHaveBeenCalledWith(entity);
          expect(mockActiveModal.close).toHaveBeenCalledWith('reset');
        })
      ));

      it('Should dismiss on clear', () => {
        // WHEN
        comp.cancel();

        // THEN
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
