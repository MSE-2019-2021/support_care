jest.mock('ng-jhipster');
jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Location } from '@angular/common';

import { SettingsCancelDialogComponent } from 'app/account/settings/settings-cancel-dialog.component';

describe('Component Tests', () => {
  describe('PSettings Management Cancel Component', () => {
    let comp: SettingsCancelDialogComponent;
    let fixture: ComponentFixture<SettingsCancelDialogComponent>;
    let mockEventManager: JhiEventManager;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SettingsCancelDialogComponent],
        providers: [NgbActiveModal, JhiEventManager, Location],
      })
        .overrideTemplate(SettingsCancelDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SettingsCancelDialogComponent);
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
          expect(mockEventManager.broadcast).toHaveBeenCalled();
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
