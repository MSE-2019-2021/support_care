jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { UserManagementResetUserPasswordDialogComponent } from './user-management-reset-user-password-dialog.component';

describe('Component Tests', () => {
  describe('User Management Reset User Password Component', () => {
    let comp: UserManagementResetUserPasswordDialogComponent;
    let fixture: ComponentFixture<UserManagementResetUserPasswordDialogComponent>;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [UserManagementResetUserPasswordDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(UserManagementResetUserPasswordDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserManagementResetUserPasswordDialogComponent);
      comp = fixture.componentInstance;
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmCancel', () => {
      it('Should close on confirmCancel', inject(
        [],
        fakeAsync(() => {
          // WHEN
          comp.confirmReset();
          tick();

          // THEN
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
