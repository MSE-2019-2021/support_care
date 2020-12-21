jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { NavbarLogoutDialogComponent } from './navbar-logout-dialog.component';

describe('Component Tests', () => {
  describe('Navbar Management Close Component', () => {
    let comp: NavbarLogoutDialogComponent;
    let fixture: ComponentFixture<NavbarLogoutDialogComponent>;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NavbarLogoutDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(NavbarLogoutDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NavbarLogoutDialogComponent);
      comp = fixture.componentInstance;
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmLogout', () => {
      it('Should close on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // WHEN
          comp.confirmLogout();
          tick();

          // THEN
          expect(mockActiveModal.close).toHaveBeenCalledWith('logout');
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
