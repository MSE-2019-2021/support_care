import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ToxicityRateComponent } from './toxicity-rate.component';
import { ToxicityRateDetailComponent } from './toxicity-rate-detail.component';
import { ToxicityRateUpdateComponent } from './toxicity-rate-update.component';
import { ToxicityRateDeleteDialogComponent } from './toxicity-rate-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [ToxicityRateComponent, ToxicityRateDetailComponent, ToxicityRateUpdateComponent, ToxicityRateDeleteDialogComponent],
  exports: [ToxicityRateDetailComponent, ToxicityRateUpdateComponent, ToxicityRateDeleteDialogComponent],
  entryComponents: [ToxicityRateDeleteDialogComponent],
})
export class ToxicityRateModule {}
