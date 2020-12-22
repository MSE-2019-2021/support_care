import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ActiveSubstanceComponent } from './list/active-substance.component';
import { ActiveSubstanceDetailComponent } from './detail/active-substance-detail.component';
import { ActiveSubstanceUpdateComponent } from './update/active-substance-update.component';
import { ActiveSubstanceDeleteDialogComponent } from './delete/active-substance-delete-dialog.component';
import { ActiveSubstanceRoutingModule } from './route/active-substance-routing.module';

@NgModule({
  imports: [SharedModule, ActiveSubstanceRoutingModule],
  declarations: [
    ActiveSubstanceComponent,
    ActiveSubstanceDetailComponent,
    ActiveSubstanceUpdateComponent,
    ActiveSubstanceDeleteDialogComponent,
  ],
  entryComponents: [ActiveSubstanceDeleteDialogComponent],
})
export class ActiveSubstanceModule {}
