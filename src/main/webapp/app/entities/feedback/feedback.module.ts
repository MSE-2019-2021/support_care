import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { FeedbackComponent } from './list/feedback.component';
import { FeedbackDetailComponent } from './detail/feedback-detail.component';
import { FeedbackUpdateComponent } from './update/feedback-update.component';
import { FeedbackDeleteDialogComponent } from './delete/feedback-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [FeedbackComponent, FeedbackDetailComponent, FeedbackUpdateComponent, FeedbackDeleteDialogComponent],
  exports: [FeedbackDetailComponent, FeedbackUpdateComponent, FeedbackDeleteDialogComponent],
  entryComponents: [FeedbackDeleteDialogComponent],
})
export class FeedbackModule {}
