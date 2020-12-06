import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { DrugComponent } from './list/drug.component';
import { DrugDetailComponent } from './detail/drug-detail.component';
import { DrugUpdateComponent } from './update/drug-update.component';
import { DrugDeleteDialogComponent } from './delete/drug-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [DrugComponent, DrugDetailComponent, DrugUpdateComponent, DrugDeleteDialogComponent],
  exports: [DrugDetailComponent, DrugUpdateComponent, DrugDeleteDialogComponent],
  entryComponents: [DrugDeleteDialogComponent],
})
export class DrugModule {}
