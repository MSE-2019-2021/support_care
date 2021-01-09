import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeedback } from '../feedback.model';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';

@Component({
  selector: 'custom-feedback-detail',
  templateUrl: './feedback-detail.component.html',
})
export class FeedbackDetailComponent implements OnInit {
  feedback: IFeedback | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedback }) => {
      this.feedback = feedback;
    });
  }

  previousState(): void {
    window.history.back();
  }

  getEntityFeedbackKey(data: EntityFeedback): string {
    return Object.keys(EntityFeedback)[Object.values(EntityFeedback).indexOf(data)];
  }
}
