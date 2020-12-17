import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { TreatmentComponent } from './list/treatment.component';
import { TreatmentDetailComponent } from './detail/treatment-detail.component';
import { TreatmentUpdateComponent } from './update/treatment-update.component';
import { TreatmentDeleteDialogComponent } from './delete/treatment-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [TreatmentComponent, TreatmentDetailComponent, TreatmentUpdateComponent, TreatmentDeleteDialogComponent],
  exports: [TreatmentDetailComponent, TreatmentUpdateComponent, TreatmentDeleteDialogComponent],
  entryComponents: [TreatmentDeleteDialogComponent],
})
export class TreatmentModule {}
