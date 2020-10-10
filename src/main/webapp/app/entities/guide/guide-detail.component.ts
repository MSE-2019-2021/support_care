import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGuide } from 'app/shared/model/guide.model';

@Component({
  selector: 'jhi-guide-detail',
  templateUrl: './guide-detail.component.html',
})
export class GuideDetailComponent implements OnInit {
  guide: IGuide | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guide }) => (this.guide = guide));
  }

  previousState(): void {
    window.history.back();
  }
}
