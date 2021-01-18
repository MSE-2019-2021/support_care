import { Component, Input } from '@angular/core';
import { IFeedback } from 'app/entities/feedback/feedback.model';

@Component({
  selector: 'custom-feedback-entity-list-header',
  templateUrl: './feedback-entity-list-header.component.html',
})
export class FeedbackEntityListHeaderComponent {
  @Input() feedbacks: IFeedback[];

  constructor() {
    this.feedbacks = [];
  }
}
