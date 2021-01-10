import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TherapeuticRegime } from '../therapeutic-regime.model';
import { TherapeuticRegimeDetailComponent } from './therapeutic-regime-detail.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';
import { Feedback } from 'app/entities/feedback/feedback.model';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TherapeuticRegimeDeleteDialogComponent } from 'app/entities/therapeutic-regime/delete/therapeutic-regime-delete-dialog.component';

export class MockNgbModalRef {
  componentInstance = {
    prompt: undefined,
    title: undefined,
  };
  result: Promise<any> = new Promise(resolve => resolve(true));
}

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Detail Component', () => {
    let comp: TherapeuticRegimeDetailComponent;
    let fixture: ComponentFixture<TherapeuticRegimeDetailComponent>;
    let service: FeedbackService;
    let modalService: NgbModal;
    let mockModalRef: MockNgbModalRef;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TherapeuticRegimeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ therapeuticRegime: new TherapeuticRegime(123) }) },
          },
        ],
      })
        .overrideTemplate(TherapeuticRegimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TherapeuticRegimeDetailComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FeedbackService);
      modalService = TestBed.inject(NgbModal);
      mockModalRef = new MockNgbModalRef();
    });

    describe('OnInit', () => {
      it('Should load therapeuticRegime on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.therapeuticRegime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('previousState', () => {
      it('Should go back', () => {
        // WHEN
        comp.previousState();

        // THEN
        expect(comp.therapeuticRegime).toEqual(jasmine.objectContaining(null));
      });
    });

    describe('delete', () => {
      it('should open modal when clicking on delete button', fakeAsync(() => {
        // GIVEN
        const therapeuticRegime = new TherapeuticRegime(123);
        spyOn(modalService, 'open').and.returnValue(mockModalRef);

        // WHEN
        comp.delete(therapeuticRegime);

        // THEN
        expect(modalService.open).toHaveBeenCalledWith(TherapeuticRegimeDeleteDialogComponent, {
          centered: true,
          size: 'lg',
          backdrop: 'static',
        });
      }));
    });

    describe('load feedbacks', () => {
      it('should load a page', () => {
        // GIVEN
        const headers = new HttpHeaders().append('link', 'link;link');
        spyOn(service, 'query').and.returnValue(
          of(
            new HttpResponse({
              body: [new Feedback(123)],
              headers,
            })
          )
        );

        // WHEN
        comp.loadPage(1);

        // THEN
        expect(service.query).toHaveBeenCalled();
        expect(comp.feedbacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
      });

      it('should return id', () => {
        // WHEN
        const result = comp.trackId(1, new Feedback(123));

        // THEN
        expect(result).toEqual(123);
      });
    });

    describe('manage feedback', () => {
      it('should save thumb up', () => {
        // GIVEN
        const feedback = new Feedback();
        feedback.entityName = EntityFeedback.THERAPEUTIC_REGIME;
        spyOn(service, 'manageFeedbackFromEntity').and.returnValue(of());

        // WHEN
        comp.manageFeedback(true);

        // THEN
        expect(service.manageFeedbackFromEntity).toHaveBeenCalled();
      });
    });
  });
});
