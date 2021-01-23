jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SymptomCancelDialogComponent } from './symptom-cancel-dialog.component';

describe('Component Tests', () => {
  describe('Symptom Management Cancel Component', () => {
    let comp: SymptomCancelDialogComponent;
    let fixture: ComponentFixture<SymptomCancelDialogComponent>;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SymptomCancelDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(SymptomCancelDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomCancelDialogComponent);
      comp = fixture.componentInstance;
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmCancel', () => {
      it('Should close on confirmCancel', inject(
        [],
        fakeAsync(() => {
          // WHEN
          comp.confirmCancel();
          tick();

          // THEN
          expect(mockActiveModal.close).toHaveBeenCalledWith('canceled');
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
