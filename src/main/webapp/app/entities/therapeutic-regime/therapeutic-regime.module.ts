import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { TherapeuticRegimeComponent } from './list/therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from './detail/therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from './update/therapeutic-regime-update.component';
import { TherapeuticRegimeDeleteDialogComponent } from './delete/therapeutic-regime-delete-dialog.component';
import { TherapeuticRegimeCancelDialogComponent } from './cancel/therapeutic-regime-cancel-dialog.component';
import { TherapeuticRegimeRoutingModule } from './route/therapeutic-regime-routing.module';
import { FeedbackEntityListModule } from 'app/entities/feedback/feedback-entity-list.module';

@NgModule({
  imports: [SharedModule, TherapeuticRegimeRoutingModule, NgMultiSelectDropDownModule, FeedbackEntityListModule],
  declarations: [
    TherapeuticRegimeComponent,
    TherapeuticRegimeDetailComponent,
    TherapeuticRegimeUpdateComponent,
    TherapeuticRegimeDeleteDialogComponent,
    TherapeuticRegimeCancelDialogComponent,
  ],
  entryComponents: [TherapeuticRegimeDeleteDialogComponent, TherapeuticRegimeCancelDialogComponent],
})
export class TherapeuticRegimeModule {}
