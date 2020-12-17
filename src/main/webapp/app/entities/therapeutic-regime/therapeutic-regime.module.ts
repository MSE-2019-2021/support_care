import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { TherapeuticRegimeComponent } from './list/therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from './detail/therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from './update/therapeutic-regime-update.component';
import { TherapeuticRegimeDeleteDialogComponent } from './delete/therapeutic-regime-delete-dialog.component';

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
