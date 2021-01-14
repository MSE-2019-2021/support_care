import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { OutcomeDetailComponent } from 'app/entities/outcome/detail/outcome-detail.component';
import { Outcome } from 'app/entities/outcome/outcome.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OutcomeDeleteDialogComponent } from 'app/entities/outcome/delete/outcome-delete-dialog.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';
import { Feedback } from 'app/entities/feedback/feedback.model';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';

export class MockNgbModalRef {
  componentInstance = {
    prompt: undefined,
    title: undefined,
  };
  result: Promise<any> = new Promise(resolve => resolve(true));
}

describe('Component Tests', () => {
  describe('Outcome Management Detail Component', () => {
    let comp: OutcomeDetailComponent;
    let fixture: ComponentFixture<OutcomeDetailComponent>;
    let modalService: NgbModal;
    let mockModalRef: MockNgbModalRef;
    let service: FeedbackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OutcomeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ outcome: new Outcome(123) }) },
          },
        ],
      })
        .overrideTemplate(OutcomeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutcomeDetailComponent);
      comp = fixture.componentInstance;
      modalService = TestBed.inject(NgbModal);
      mockModalRef = new MockNgbModalRef();
      service = TestBed.inject(FeedbackService);
    });

    describe('OnInit', () => {
      it('Should load outcome on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.outcome).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('delete', () => {
      it('should open modal when clicking on delete button', fakeAsync(() => {
        // GIVEN
        const outcome = new Outcome(123);
        spyOn(modalService, 'open').and.returnValue(mockModalRef);

        // WHEN
        comp.delete(outcome);

        // THEN
        expect(modalService.open).toHaveBeenCalledWith(OutcomeDeleteDialogComponent, {
          centered: true,
          size: 'lg',
          backdrop: 'static',
        });
      }));
    });

    describe('previousState', () => {
      it('Should go back', () => {
        // WHEN
        comp.previousState();

        // THEN
        expect(comp.outcome).toEqual(jasmine.objectContaining(null));
      });
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
        feedback.entityName = EntityFeedback.OUTCOME;
        spyOn(service, 'manageFeedbackFromEntity').and.returnValue(of());

        // WHEN
        comp.manageFeedback(true);

        // THEN
        expect(service.manageFeedbackFromEntity).toHaveBeenCalled();
      });
    });
  });
});
