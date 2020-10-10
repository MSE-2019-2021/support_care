import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SupportcareSharedModule } from 'app/shared/shared.module';
import { TherapeuticRegimeComponent } from './therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from './therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from './therapeutic-regime-update.component';
import { TherapeuticRegimeDeleteDialogComponent } from './therapeutic-regime-delete-dialog.component';
import { therapeuticRegimeRoute } from './therapeutic-regime.route';

@NgModule({
  imports: [SupportcareSharedModule, RouterModule.forChild(therapeuticRegimeRoute)],
  declarations: [
    TherapeuticRegimeComponent,
    TherapeuticRegimeDetailComponent,
    TherapeuticRegimeUpdateComponent,
    TherapeuticRegimeDeleteDialogComponent,
  ],
  entryComponents: [TherapeuticRegimeDeleteDialogComponent],
})
export class SupportcareTherapeuticRegimeModule {}
