import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { TherapeuticRegimeComponent } from './list/therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from './detail/therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from './update/therapeutic-regime-update.component';
import { TherapeuticRegimeDeleteDialogComponent } from './delete/therapeutic-regime-delete-dialog.component';
import { TherapeuticRegimeCancelDialogComponent } from './cancel/therapeutic-regime-cancel-dialog.component';
import { TherapeuticRegimeRoutingModule } from './route/therapeutic-regime-routing.module';
import { DefineReasonDialogComponent } from '../feedback/define-reason/define-reason-dialog.component';

@NgModule({
  imports: [SharedModule, TherapeuticRegimeRoutingModule, NgMultiSelectDropDownModule],
  declarations: [
    TherapeuticRegimeComponent,
    TherapeuticRegimeDetailComponent,
    TherapeuticRegimeUpdateComponent,
    TherapeuticRegimeDeleteDialogComponent,
    TherapeuticRegimeCancelDialogComponent,
    DefineReasonDialogComponent,
  ],
  entryComponents: [TherapeuticRegimeDeleteDialogComponent, TherapeuticRegimeCancelDialogComponent, DefineReasonDialogComponent],
})
export class TherapeuticRegimeModule {}
