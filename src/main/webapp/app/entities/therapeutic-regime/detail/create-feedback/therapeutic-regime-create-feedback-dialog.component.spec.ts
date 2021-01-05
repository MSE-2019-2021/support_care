jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TherapeuticRegimeCreateFeedbackDialogComponent } from './therapeutic-regime-create-feedback-dialog.component';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Cancel Component', () => {
    let comp: TherapeuticRegimeCreateFeedbackDialogComponent;
    let fixture: ComponentFixture<TherapeuticRegimeCreateFeedbackDialogComponent>;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TherapeuticRegimeCreateFeedbackDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(TherapeuticRegimeCreateFeedbackDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TherapeuticRegimeCreateFeedbackDialogComponent);
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
