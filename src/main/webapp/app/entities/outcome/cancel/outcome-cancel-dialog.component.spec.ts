jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { OutcomeCancelDialogComponent } from './outcome-cancel-dialog.component';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Cancel Component', () => {
    let comp: OutcomeCancelDialogComponent;
    let fixture: ComponentFixture<OutcomeCancelDialogComponent>;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OutcomeCancelDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(OutcomeCancelDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutcomeCancelDialogComponent);
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
