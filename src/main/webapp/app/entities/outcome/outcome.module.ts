import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SupportcareSharedModule } from 'app/shared/shared.module';
import { OutcomeComponent } from './outcome.component';
import { OutcomeDetailComponent } from './outcome-detail.component';
import { OutcomeUpdateComponent } from './outcome-update.component';
import { OutcomeDeleteDialogComponent } from './outcome-delete-dialog.component';
import { outcomeRoute } from './outcome.route';

@NgModule({
  imports: [SupportcareSharedModule, RouterModule.forChild(outcomeRoute)],
  declarations: [OutcomeComponent, OutcomeDetailComponent, OutcomeUpdateComponent, OutcomeDeleteDialogComponent],
  entryComponents: [OutcomeDeleteDialogComponent],
})
export class SupportcareOutcomeModule {}
