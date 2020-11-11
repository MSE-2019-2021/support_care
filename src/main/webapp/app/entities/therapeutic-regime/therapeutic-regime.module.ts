import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { TherapeuticRegimeComponent } from './therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from './therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from './therapeutic-regime-update.component';
import { TherapeuticRegimeDeleteDialogComponent } from './therapeutic-regime-delete-dialog.component';
import { TherapeuticRegimeCancelDialogComponent } from './therapeutic-regime-cancel-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [
    TherapeuticRegimeComponent,
    TherapeuticRegimeDetailComponent,
    TherapeuticRegimeUpdateComponent,
    TherapeuticRegimeDeleteDialogComponent,
    TherapeuticRegimeCancelDialogComponent,
  ],
  exports: [TherapeuticRegimeDetailComponent, TherapeuticRegimeUpdateComponent, TherapeuticRegimeDeleteDialogComponent],
  entryComponents: [TherapeuticRegimeDeleteDialogComponent, TherapeuticRegimeCancelDialogComponent],
})
export class TherapeuticRegimeModule {}
