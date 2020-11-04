import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { AdministrationComponent } from './administration.component';
import { AdministrationDetailComponent } from './administration-detail.component';
import { AdministrationUpdateComponent } from './administration-update.component';
import { AdministrationDeleteDialogComponent } from './administration-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [
    AdministrationComponent,
    AdministrationDetailComponent,
    AdministrationUpdateComponent,
    AdministrationDeleteDialogComponent,
  ],
  exports: [AdministrationDetailComponent, AdministrationUpdateComponent, AdministrationDeleteDialogComponent],
  entryComponents: [AdministrationDeleteDialogComponent],
})
export class AdministrationModule {}
