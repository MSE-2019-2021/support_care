import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { DrugComponent } from './drug.component';
import { DrugDetailComponent } from './drug-detail.component';
import { DrugUpdateComponent } from './drug-update.component';
import { DrugDeleteDialogComponent } from './drug-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [DrugComponent, DrugDetailComponent, DrugUpdateComponent, DrugDeleteDialogComponent],
  exports: [DrugDetailComponent, DrugUpdateComponent, DrugDeleteDialogComponent],
  entryComponents: [DrugDeleteDialogComponent],
})
export class DrugModule {}
