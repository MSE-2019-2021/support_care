jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FeedbackService } from '../service/feedback.service';

import { FeedbackDeleteDialogComponent } from './feedback-delete-dialog.component';

describe('Component Tests', () => {
  describe('Feedback Management Delete Component', () => {
    let comp: FeedbackDeleteDialogComponent;
    let fixture: ComponentFixture<FeedbackDeleteDialogComponent>;
    let service: FeedbackService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FeedbackDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(FeedbackDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeedbackDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FeedbackService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call deleteSolved service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'deleteSolved').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete();
          tick();

          // THEN
          expect(service.deleteSolved).toHaveBeenCalled();
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call deleteSolved service on clear', () => {
        // GIVEN
        spyOn(service, 'deleteSolved');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.deleteSolved).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
