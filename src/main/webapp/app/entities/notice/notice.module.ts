import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { NoticeComponent } from './notice.component';
import { NoticeDetailComponent } from './notice-detail.component';
import { NoticeUpdateComponent } from './notice-update.component';
import { NoticeDeleteDialogComponent } from './notice-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [NoticeComponent, NoticeDetailComponent, NoticeUpdateComponent, NoticeDeleteDialogComponent],
  exports: [NoticeDetailComponent, NoticeUpdateComponent, NoticeDeleteDialogComponent],
  entryComponents: [NoticeDeleteDialogComponent],
})
export class NoticeModule {}
