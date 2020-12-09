import { NavbarLogoutDialogComponent } from 'app/layouts/navbar/navbar-logout-dialog.component';

jest.mock('ng-jhipster');
jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Location } from '@angular/common';

import { TherapeuticRegimeCancelDialogComponent } from 'app/entities/therapeutic-regime/therapeutic-regime-cancel-dialog.component';
import { LoginService } from 'app/login/login.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Cancel Component', () => {
    let comp: NavbarLogoutDialogComponent;
    let fixture: ComponentFixture<NavbarLogoutDialogComponent>;
    let mockActiveModal: NgbActiveModal;
    let mockLoginService: LoginService;

    const mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NavbarLogoutDialogComponent],
        providers: [
          NgbActiveModal,
          Location,
          { provide: LoginService, useValue: { login: jest.fn(() => of({})) } },
          { provide: Router, useValue: mockRouter },
        ],
      })
        .overrideTemplate(TherapeuticRegimeCancelDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NavbarLogoutDialogComponent);
      comp = fixture.componentInstance;
      mockActiveModal = TestBed.inject(NgbActiveModal);
      mockLoginService = TestBed.inject(LoginService);
    });

    describe('logout', () => {
      it('Should cancel logout when clicking on cancel button and close modal', inject(
        [],
        fakeAsync(() => {
          // WHEN
          comp.cancel();
          tick();

          // THEN
          expect(mockActiveModal.dismiss).toHaveBeenCalled();
        })
      ));

      it('Should log out when clicking leave button', () => {
        // GIVEN
        mockLoginService.logout = jest.fn(() => of(null));

        // WHEN
        comp.logout();

        // THEN
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
        expect(mockLoginService.logout).toHaveBeenCalled();
        expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
      });
    });
  });
});
