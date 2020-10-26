import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SupportivecareSharedModule } from 'app/shared/shared.module';
import { AdministrationComponent } from './administration.component';
import { AdministrationDetailComponent } from './administration-detail.component';
import { AdministrationUpdateComponent } from './administration-update.component';
import { AdministrationDeleteDialogComponent } from './administration-delete-dialog.component';
import { administrationRoute } from './administration.route';

@NgModule({
  imports: [SupportivecareSharedModule, RouterModule.forChild(administrationRoute)],
  declarations: [
    AdministrationComponent,
    AdministrationDetailComponent,
    AdministrationUpdateComponent,
    AdministrationDeleteDialogComponent,
  ],
  entryComponents: [AdministrationDeleteDialogComponent],
})
export class SupportivecareAdministrationModule {}
