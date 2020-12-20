import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TherapeuticRegimeComponent } from './list/therapeutic-regime.component';
import { TherapeuticRegimeDetailComponent } from './detail/therapeutic-regime-detail.component';
import { TherapeuticRegimeUpdateComponent } from './update/therapeutic-regime-update.component';
import { TherapeuticRegimeDeleteDialogComponent } from './delete/therapeutic-regime-delete-dialog.component';
import { TherapeuticRegimeRoutingModule } from './route/therapeutic-regime-routing.module';

@NgModule({
  imports: [SharedModule, TherapeuticRegimeRoutingModule],
  declarations: [
    TherapeuticRegimeComponent,
    TherapeuticRegimeDetailComponent,
    TherapeuticRegimeUpdateComponent,
    TherapeuticRegimeDeleteDialogComponent,
  ],
  entryComponents: [TherapeuticRegimeDeleteDialogComponent],
})
export class TherapeuticRegimeModule {}
