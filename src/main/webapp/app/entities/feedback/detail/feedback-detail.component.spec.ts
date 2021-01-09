import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Feedback } from '../feedback.model';

import { FeedbackDetailComponent } from './feedback-detail.component';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';

describe('Component Tests', () => {
  describe('Feedback Management Detail Component', () => {
    let comp: FeedbackDetailComponent;
    let fixture: ComponentFixture<FeedbackDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FeedbackDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ feedback: new Feedback(123) }) },
          },
        ],
      })
        .overrideTemplate(FeedbackDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeedbackDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load feedback on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feedback).toEqual(jasmine.objectContaining({ id: 123 }));
      });

      it('should return Entity Feedback value', () => {
        // WHEN
        const result = comp.getEntityFeedbackKey(EntityFeedback.THERAPEUTIC_REGIME);

        // THEN
        expect(result).toEqual('THERAPEUTIC_REGIME');
      });
    });
  });
});
