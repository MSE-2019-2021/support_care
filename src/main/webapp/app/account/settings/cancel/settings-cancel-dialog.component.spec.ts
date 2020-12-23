jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Location } from '@angular/common';

import { SettingsCancelDialogComponent } from 'app/account/settings/cancel/settings-cancel-dialog.component';

describe('Component Tests', () => {
  describe('PSettings Management Cancel Component', () => {
    let comp: SettingsCancelDialogComponent;
    let fixture: ComponentFixture<SettingsCancelDialogComponent>;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SettingsCancelDialogComponent],
        providers: [NgbActiveModal, Location],
      })
        .overrideTemplate(SettingsCancelDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SettingsCancelDialogComponent);
      comp = fixture.componentInstance;
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
