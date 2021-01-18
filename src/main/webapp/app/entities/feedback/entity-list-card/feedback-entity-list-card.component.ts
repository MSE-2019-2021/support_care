import { Component, Input } from '@angular/core';
import { IFeedback } from 'app/entities/feedback/feedback.model';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

@Component({
  selector: 'custom-feedback-entity-list-card',
  templateUrl: './feedback-entity-list-card.component.html',
})
export class FeedbackEntityListCardComponent {
  @Input() feedback: IFeedback;
  dateFormat: string;

  constructor() {
    this.feedback = {};
    this.dateFormat = DATE_TIME_FORMAT;
  }
}
