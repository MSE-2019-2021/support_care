import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SupportcareSharedModule } from 'app/shared/shared.module';
import { GuideComponent } from './guide.component';
import { GuideDetailComponent } from './guide-detail.component';
import { GuideUpdateComponent } from './guide-update.component';
import { GuideDeleteDialogComponent } from './guide-delete-dialog.component';
import { guideRoute } from './guide.route';

@NgModule({
  imports: [SupportcareSharedModule, RouterModule.forChild(guideRoute)],
  declarations: [GuideComponent, GuideDetailComponent, GuideUpdateComponent, GuideDeleteDialogComponent],
  entryComponents: [GuideDeleteDialogComponent],
})
export class SupportcareGuideModule {}
