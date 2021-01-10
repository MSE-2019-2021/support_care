import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Symptom } from '../symptom.model';
import { SymptomDetailComponent } from './symptom-detail.component';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';
import { Feedback } from 'app/entities/feedback/feedback.model';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';

describe('Component Tests', () => {
  describe('Symptom Management Detail Component', () => {
    let comp: SymptomDetailComponent;
    let fixture: ComponentFixture<SymptomDetailComponent>;
    let service: FeedbackService;

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
      service = TestBed.inject(FeedbackService);
    });

    describe('OnInit', () => {
      it('Should load symptom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.symptom).toEqual(jasmine.objectContaining({ id: 123 }));
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
        feedback.entityName = EntityFeedback.SYMPTOM;
        spyOn(service, 'manageFeedbackFromEntity').and.returnValue(of());

        // WHEN
        comp.manageFeedback(true);

        // THEN
        expect(service.manageFeedbackFromEntity).toHaveBeenCalled();
      });
    });
  });
});
