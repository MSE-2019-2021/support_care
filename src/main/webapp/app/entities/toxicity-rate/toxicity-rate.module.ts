import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ToxicityRateComponent } from './list/toxicity-rate.component';
import { ToxicityRateDetailComponent } from './detail/toxicity-rate-detail.component';
import { ToxicityRateUpdateComponent } from './update/toxicity-rate-update.component';
import { ToxicityRateDeleteDialogComponent } from './delete/toxicity-rate-delete-dialog.component';
import { ToxicityRateRoutingModule } from './route/toxicity-rate-routing.module';

@NgModule({
  imports: [SharedModule, ToxicityRateRoutingModule],
  declarations: [ToxicityRateComponent, ToxicityRateDetailComponent, ToxicityRateUpdateComponent, ToxicityRateDeleteDialogComponent],
  entryComponents: [ToxicityRateDeleteDialogComponent],
})
export class ToxicityRateModule {}
