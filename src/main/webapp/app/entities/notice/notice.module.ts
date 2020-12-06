import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { NoticeComponent } from './list/notice.component';
import { NoticeDetailComponent } from './detail/notice-detail.component';
import { NoticeUpdateComponent } from './update/notice-update.component';
import { NoticeDeleteDialogComponent } from './delete/notice-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [NoticeComponent, NoticeDetailComponent, NoticeUpdateComponent, NoticeDeleteDialogComponent],
  exports: [NoticeDetailComponent, NoticeUpdateComponent, NoticeDeleteDialogComponent],
  entryComponents: [NoticeDeleteDialogComponent],
})
export class NoticeModule {}
