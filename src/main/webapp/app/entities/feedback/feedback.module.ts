import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FeedbackComponent } from './list/feedback.component';
import { FeedbackDeleteDialogComponent } from './delete/feedback-delete-dialog.component';
import { FeedbackRoutingModule } from './route/feedback-routing.module';

@NgModule({
  imports: [SharedModule, FeedbackRoutingModule],
  declarations: [FeedbackComponent, FeedbackDeleteDialogComponent],
  entryComponents: [FeedbackDeleteDialogComponent],
})
export class FeedbackModule {}
