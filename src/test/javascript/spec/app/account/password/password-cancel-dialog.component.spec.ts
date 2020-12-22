jest.mock('ng-jhipster');
jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Location } from '@angular/common';

import { PasswordCancelDialogComponent } from 'app/account/password/password-cancel-dialog.component';

describe('Component Tests', () => {
  describe('Password Management Cancel Component', () => {
    let comp: PasswordCancelDialogComponent;
    let fixture: ComponentFixture<PasswordCancelDialogComponent>;
    let mockEventManager: JhiEventManager;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PasswordCancelDialogComponent],
        providers: [NgbActiveModal, JhiEventManager, Location],
      })
        .overrideTemplate(PasswordCancelDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PasswordCancelDialogComponent);
      comp = fixture.componentInstance;
      mockEventManager = TestBed.inject(JhiEventManager);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmCancel', () => {
      it('Should call cancel on confirmCancel', inject(
        [],
        fakeAsync(() => {
          // WHEN
          comp.confirmCancel();
          tick();

          // THEN
          expect(mockActiveModal.close).toHaveBeenCalled();
          //expect(mockEventManager.broadcast).toHaveBeenCalled();
        })
      ));

      it('Should not call cancel son confirmCancel', () => {
        // WHEN
        comp.cancel();

        // THEN
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });

    describe('goBack', () => {
      it('Should go back', inject(
        [],
        fakeAsync(() => {
          // WHEN
          comp.goBack();
          tick();

          // THEN
        })
      ));
    });
  });
});
