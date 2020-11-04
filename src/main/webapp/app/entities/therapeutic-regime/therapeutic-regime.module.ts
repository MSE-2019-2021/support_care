import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { TherapeuticRegimeComponent } from './therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from './therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from './therapeutic-regime-update.component';
import { TherapeuticRegimeDeleteDialogComponent } from './therapeutic-regime-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [
    TherapeuticRegimeComponent,
    TherapeuticRegimeDetailComponent,
    TherapeuticRegimeUpdateComponent,
    TherapeuticRegimeDeleteDialogComponent,
  ],
  exports: [TherapeuticRegimeDetailComponent, TherapeuticRegimeUpdateComponent, TherapeuticRegimeDeleteDialogComponent],
  entryComponents: [TherapeuticRegimeDeleteDialogComponent],
})
export class TherapeuticRegimeModule {}
