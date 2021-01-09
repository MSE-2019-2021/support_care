import { Feedback } from 'app/entities/feedback/feedback.model';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FeedbackService } from '../service/feedback.service';

import { DefineReasonDialogComponent } from './define-reason-dialog.component';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

jest.mock('@ng-bootstrap/ng-bootstrap');

describe('Component Tests', () => {
  describe('Feedback Management Delete Component', () => {
    let comp: DefineReasonDialogComponent;
    let fixture: ComponentFixture<DefineReasonDialogComponent>;
    let service: FeedbackService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DefineReasonDialogComponent],
        providers: [NgbActiveModal, FormBuilder],
      })
        .overrideTemplate(DefineReasonDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DefineReasonDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FeedbackService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('ngOnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN
        comp.feedback = new Feedback(123);

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feedback).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('confirmSave', () => {
      it('Should call manage service on confirmSave', fakeAsync(() => {
        // GIVEN
        comp.feedback = new Feedback(123);
        spyOn(service, 'manageFeedbackFromEntity').and.returnValue(of({}));

        // WHEN
        comp.confirm();
        tick();

        // THEN
        expect(service.manageFeedbackFromEntity).toHaveBeenCalledWith(comp.feedback);
        expect(mockActiveModal.close).toHaveBeenCalledWith('saved');
      }));

      it('Should call manage service on clear', fakeAsync(() => {
        // GIVEN
        comp.feedback = new Feedback(123);
        spyOn(service, 'manageFeedbackFromEntity').and.returnValue(of({}));

        // WHEN
        comp.cancel();
        tick();

        // THEN
        expect(service.manageFeedbackFromEntity).toHaveBeenCalledWith(comp.feedback);
        expect(mockActiveModal.close).toHaveBeenCalledWith('saved');
      }));
    });
  });
});
