import { Component, Input } from '@angular/core';
import { IFeedback } from 'app/entities/feedback/feedback.model';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';

@Component({
  selector: 'custom-feedback-entity-list-card',
  templateUrl: './feedback-entity-list-card.component.html',
})
export class FeedbackEntityListCardComponent {
  @Input() feedback: IFeedback;
  @Input() feedbacks: IFeedback[];
  @Input() i: number;
  dateFormat: string;

  constructor(protected feedbackService: FeedbackService) {
    this.i = 0;
    this.feedbacks = [];
    this.feedback = {};
    this.dateFormat = DATE_TIME_FORMAT;
  }

  markFeedbackAsSolved(feedback: IFeedback, feedbacks: IFeedback[], i: number): void {
    feedback.solved = true;
    this.feedbackService.update(feedback).subscribe(() => {
      this.feedbacks.splice(i, 1);
    });
  }
}
