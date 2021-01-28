import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Outcome } from 'app/entities/outcome/outcome.model';
import { OutcomeDetailComponent } from 'app/entities/outcome/detail/outcome-detail.component';
import { OutcomeDeleteDialogComponent } from 'app/entities/outcome/delete/outcome-delete-dialog.component';
import { Thumb } from 'app/entities/thumb/thumb.model';
import { ThumbCount } from 'app/entities/thumb/thumb-count.model';
import { ThumbService } from 'app/entities/thumb/service/thumb.service';
import { Feedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';
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
    let thumbService: ThumbService;
    let feedbackService: FeedbackService;

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
      thumbService = TestBed.inject(ThumbService);
      feedbackService = TestBed.inject(FeedbackService);
    });

    describe('OnInit', () => {
      it('Should load outcome on init', () => {
        // GIVEN
        spyOn(thumbService, 'countThumbsFromEntity').and.returnValue(
          of(
            new HttpResponse({
              body: new ThumbCount(1, 2, false),
            })
          )
        );
        const headers = new HttpHeaders().append('link', 'link;link');
        spyOn(feedbackService, 'query').and.returnValue(
          of(
            new HttpResponse({
              body: [new Feedback(123)],
              headers,
            })
          )
        );

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.outcome).toEqual(jasmine.objectContaining({ id: 123 }));
        expect(thumbService.countThumbsFromEntity).toHaveBeenCalled();
        expect(comp.thumbCount).toEqual(jasmine.objectContaining({ countThumbUp: 1, countThumbDown: 2, thumb: false }));
        expect(feedbackService.query).toHaveBeenCalled();
        expect(comp.feedbacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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

    describe('load Feedbacks', () => {
      it('should load a page', () => {
        // GIVEN
        const headers = new HttpHeaders().append('link', 'link;link');
        spyOn(feedbackService, 'query').and.returnValue(
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
        expect(feedbackService.query).toHaveBeenCalled();
        expect(comp.feedbacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
      });

      it('should return id', () => {
        // WHEN
        const result = comp.trackId(1, new Feedback(123));

        // THEN
        expect(result).toEqual(123);
      });
    });

    describe('manage thumbs', () => {
      it('should save thumb up', () => {
        // GIVEN
        spyOn(thumbService, 'manageThumbFromEntity').and.returnValue(of());

        // WHEN
        comp.manageThumb(true);

        // THEN
        const thumb = new Thumb(undefined, EntityFeedback.OUTCOME, undefined, true);
        expect(thumbService.manageThumbFromEntity).toHaveBeenCalledWith(thumb);
      });

      it('should delete thumb up', () => {
        // GIVEN
        spyOn(thumbService, 'manageThumbFromEntity').and.returnValue(of());
        comp.thumbCount.thumb = true;

        // WHEN
        comp.manageThumb(true);

        // THEN
        const thumb = new Thumb(undefined, EntityFeedback.OUTCOME, undefined, undefined);
        expect(thumbService.manageThumbFromEntity).toHaveBeenCalledWith(thumb);
      });

      it('should save thumb down', () => {
        // GIVEN
        spyOn(thumbService, 'manageThumbFromEntity').and.returnValue(of({}));

        // WHEN
        comp.manageThumb(false);

        // THEN
        const thumb = new Thumb(undefined, EntityFeedback.OUTCOME, undefined, false);
        expect(thumbService.manageThumbFromEntity).toHaveBeenCalledWith(thumb);
      });

      it('should delete thumb down', () => {
        // GIVEN
        spyOn(thumbService, 'manageThumbFromEntity').and.returnValue(of({}));
        comp.thumbCount.thumb = false;

        // WHEN
        comp.manageThumb(false);

        // THEN
        const thumb = new Thumb(undefined, EntityFeedback.OUTCOME, undefined, undefined);
        expect(thumbService.manageThumbFromEntity).toHaveBeenCalledWith(thumb);
      });
    });
  });
});
