import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';

import { SharedModule } from 'app/shared/shared.module';
import { TherapeuticRegimeComponent } from './list/therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from './detail/therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from './update/therapeutic-regime-update.component';
import { TherapeuticRegimeDeleteDialogComponent } from './delete/therapeutic-regime-delete-dialog.component';
import { TherapeuticRegimeCancelDialogComponent } from './cancel/therapeutic-regime-cancel-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule, NgMultiSelectDropDownModule],
  declarations: [
    TherapeuticRegimeComponent,
    TherapeuticRegimeDetailComponent,
    TherapeuticRegimeUpdateComponent,
    TherapeuticRegimeDeleteDialogComponent,
    TherapeuticRegimeCancelDialogComponent,
  ],
  exports: [
    TherapeuticRegimeDetailComponent,
    TherapeuticRegimeUpdateComponent,
    TherapeuticRegimeDeleteDialogComponent,
    TherapeuticRegimeCancelDialogComponent,
  ],
  entryComponents: [TherapeuticRegimeDeleteDialogComponent, TherapeuticRegimeCancelDialogComponent],
})
export class TherapeuticRegimeModule {}
