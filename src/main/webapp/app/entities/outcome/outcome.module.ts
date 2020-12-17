import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { OutcomeComponent } from './list/outcome.component';
import { OutcomeDetailComponent } from './detail/outcome-detail.component';
import { OutcomeUpdateComponent } from './update/outcome-update.component';
import { OutcomeDeleteDialogComponent } from './delete/outcome-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [OutcomeComponent, OutcomeDetailComponent, OutcomeUpdateComponent, OutcomeDeleteDialogComponent],
  exports: [OutcomeDetailComponent, OutcomeUpdateComponent, OutcomeDeleteDialogComponent],
  entryComponents: [OutcomeDeleteDialogComponent],
})
export class OutcomeModule {}
