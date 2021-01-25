import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { OutcomeComponent } from './list/outcome.component';
import { OutcomeDetailComponent } from './detail/outcome-detail.component';
import { OutcomeUpdateComponent } from './update/outcome-update.component';
import { OutcomeDeleteDialogComponent } from './delete/outcome-delete-dialog.component';
import { OutcomeRoutingModule } from './route/outcome-routing.module';
import { FeedbackEntityListModule } from 'app/entities/feedback/feedback-entity-list.module';
import { OutcomeCancelDialogComponent } from 'app/entities/outcome/cancel/outcome-cancel-dialog.component';

@NgModule({
  imports: [SharedModule, OutcomeRoutingModule, FeedbackEntityListModule],
  declarations: [
    OutcomeComponent,
    OutcomeDetailComponent,
    OutcomeUpdateComponent,
    OutcomeDeleteDialogComponent,
    OutcomeCancelDialogComponent,
  ],
  entryComponents: [OutcomeDeleteDialogComponent],
})
export class OutcomeModule {}
