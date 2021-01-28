import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Symptom } from '../symptom.model';
import { SymptomDetailComponent } from './symptom-detail.component';
import { Thumb } from 'app/entities/thumb/thumb.model';
import { ThumbCount } from 'app/entities/thumb/thumb-count.model';
import { ThumbService } from 'app/entities/thumb/service/thumb.service';
import { Feedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';

describe('Component Tests', () => {
  describe('Symptom Management Detail Component', () => {
    let comp: SymptomDetailComponent;
    let fixture: ComponentFixture<SymptomDetailComponent>;
    let thumbService: ThumbService;
    let feedbackService: FeedbackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SymptomDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ symptom: new Symptom(123) }) },
          },
        ],
      })
        .overrideTemplate(SymptomDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomDetailComponent);
      comp = fixture.componentInstance;
      thumbService = TestBed.inject(ThumbService);
      feedbackService = TestBed.inject(FeedbackService);
    });

    describe('OnInit', () => {
      it('Should load symptom on init', () => {
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
        expect(comp.symptom).toEqual(jasmine.objectContaining({ id: 123 }));
        expect(thumbService.countThumbsFromEntity).toHaveBeenCalled();
        expect(comp.thumbCount).toEqual(jasmine.objectContaining({ countThumbUp: 1, countThumbDown: 2, thumb: false }));
        expect(feedbackService.query).toHaveBeenCalled();
        expect(comp.feedbacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('previousState', () => {
      it('Should go back', () => {
        // WHEN
        comp.previousState();

        // THEN
        expect(comp.symptom).toEqual(jasmine.objectContaining(null));
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
        const thumb = new Thumb(undefined, EntityFeedback.SYMPTOM, undefined, true);
        expect(thumbService.manageThumbFromEntity).toHaveBeenCalledWith(thumb);
      });

      it('should delete thumb up', () => {
        // GIVEN
        spyOn(thumbService, 'manageThumbFromEntity').and.returnValue(of());
        comp.thumbCount.thumb = true;

        // WHEN
        comp.manageThumb(true);

        // THEN
        const thumb = new Thumb(undefined, EntityFeedback.SYMPTOM, undefined, undefined);
        expect(thumbService.manageThumbFromEntity).toHaveBeenCalledWith(thumb);
      });

      it('should save thumb down', () => {
        // GIVEN
        spyOn(thumbService, 'manageThumbFromEntity').and.returnValue(of({}));

        // WHEN
        comp.manageThumb(false);

        // THEN
        const thumb = new Thumb(undefined, EntityFeedback.SYMPTOM, undefined, false);
        expect(thumbService.manageThumbFromEntity).toHaveBeenCalledWith(thumb);
      });

      it('should delete thumb down', () => {
        // GIVEN
        spyOn(thumbService, 'manageThumbFromEntity').and.returnValue(of({}));
        comp.thumbCount.thumb = false;

        // WHEN
        comp.manageThumb(false);

        // THEN
        const thumb = new Thumb(undefined, EntityFeedback.SYMPTOM, undefined, undefined);
        expect(thumbService.manageThumbFromEntity).toHaveBeenCalledWith(thumb);
      });
    });
  });
});
