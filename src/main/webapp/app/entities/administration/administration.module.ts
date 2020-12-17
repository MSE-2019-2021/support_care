import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { AdministrationComponent } from './list/administration.component';
import { AdministrationDetailComponent } from './detail/administration-detail.component';
import { AdministrationUpdateComponent } from './update/administration-update.component';
import { AdministrationDeleteDialogComponent } from './delete/administration-delete-dialog.component';

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
