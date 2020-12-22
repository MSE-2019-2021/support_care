jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TherapeuticRegimeCancelDialogComponent } from './therapeutic-regime-cancel-dialog.component';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Cancel Component', () => {
    let comp: TherapeuticRegimeCancelDialogComponent;
    let fixture: ComponentFixture<TherapeuticRegimeCancelDialogComponent>;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TherapeuticRegimeCancelDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(TherapeuticRegimeCancelDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TherapeuticRegimeCancelDialogComponent);
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
