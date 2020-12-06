import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContent } from 'app/shared/model/content.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'custom-content-detail',
  templateUrl: './content-detail.component.html',
})
export class ContentDetailComponent implements OnInit {
  content: IContent | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ content }) => {
      this.content = content;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType = ''): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
