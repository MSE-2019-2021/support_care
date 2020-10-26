import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SupportivecareSharedModule } from 'app/shared/shared.module';
import { TreatmentComponent } from './treatment.component';
import { TreatmentDetailComponent } from './treatment-detail.component';
import { TreatmentUpdateComponent } from './treatment-update.component';
import { TreatmentDeleteDialogComponent } from './treatment-delete-dialog.component';
import { treatmentRoute } from './treatment.route';

@NgModule({
  imports: [SupportivecareSharedModule, RouterModule.forChild(treatmentRoute)],
  declarations: [TreatmentComponent, TreatmentDetailComponent, TreatmentUpdateComponent, TreatmentDeleteDialogComponent],
  entryComponents: [TreatmentDeleteDialogComponent],
})
export class SupportivecareTreatmentModule {}
