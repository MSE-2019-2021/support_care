import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { OutcomeComponent } from './list/outcome.component';
import { OutcomeDetailComponent } from './detail/outcome-detail.component';
import { OutcomeUpdateComponent } from './update/outcome-update.component';
import { OutcomeDeleteDialogComponent } from './delete/outcome-delete-dialog.component';
import { OutcomeRoutingModule } from './route/outcome-routing.module';

@NgModule({
  imports: [SharedModule, OutcomeRoutingModule],
  declarations: [OutcomeComponent, OutcomeDetailComponent, OutcomeUpdateComponent, OutcomeDeleteDialogComponent],
  entryComponents: [OutcomeDeleteDialogComponent],
})
export class OutcomeModule {}
