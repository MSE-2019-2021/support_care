jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FeedbackService } from '../service/feedback.service';
import { FeedbackEntityListCardComponent } from './feedback-entity-list-card.component';
import { Feedback } from '../feedback.model';

describe('Component Tests', () => {
  describe('Feedback Entity List Card component', () => {
    let comp: FeedbackEntityListCardComponent;
    let fixture: ComponentFixture<FeedbackEntityListCardComponent>;
    let service: FeedbackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FeedbackEntityListCardComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(FeedbackEntityListCardComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeedbackEntityListCardComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FeedbackService);
    });

    describe('markFeedbackAsSolved', () => {
      it('Should mark the feedback as solved', () => {
        spyOn(service, 'update').and.returnValue(of({}));

        const feedbackTest = new Feedback(12);
        const feedbackTest2 = new Feedback(122);

        const listFeedbacks = [feedbackTest, feedbackTest2];

        comp.feedbacks = listFeedbacks;

        comp.markFeedbackAsSolved(feedbackTest, listFeedbacks, 12);

        expect(feedbackTest.solved).toBeTruthy();
        expect(comp.feedbacks).toBe(listFeedbacks);
      });
    });
  });
});
