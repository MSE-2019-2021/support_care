import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ActiveSubstanceComponent } from './list/active-substance.component';
import { ActiveSubstanceDetailComponent } from './detail/active-substance-detail.component';
import { ActiveSubstanceUpdateComponent } from './update/active-substance-update.component';
import { ActiveSubstanceDeleteDialogComponent } from './delete/active-substance-delete-dialog.component';
import { ActiveSubstanceRoutingModule } from './route/active-substance-routing.module';
import { ActiveSubstanceCancelDialogComponent } from './cancel/active-substance-cancel-dialog.component';

@NgModule({
  imports: [SharedModule, ActiveSubstanceRoutingModule],
  declarations: [
    ActiveSubstanceComponent,
    ActiveSubstanceDetailComponent,
    ActiveSubstanceUpdateComponent,
    ActiveSubstanceDeleteDialogComponent,
    ActiveSubstanceCancelDialogComponent,
  ],
  entryComponents: [ActiveSubstanceDeleteDialogComponent],
})
export class ActiveSubstanceModule {}
