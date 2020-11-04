import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { TreatmentComponent } from './treatment.component';
import { TreatmentDetailComponent } from './treatment-detail.component';
import { TreatmentUpdateComponent } from './treatment-update.component';
import { TreatmentDeleteDialogComponent } from './treatment-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [TreatmentComponent, TreatmentDetailComponent, TreatmentUpdateComponent, TreatmentDeleteDialogComponent],
  exports: [TreatmentDetailComponent, TreatmentUpdateComponent, TreatmentDeleteDialogComponent],
  entryComponents: [TreatmentDeleteDialogComponent],
})
export class TreatmentModule {}
