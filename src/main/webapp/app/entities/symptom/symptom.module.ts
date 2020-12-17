import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { SymptomComponent } from './list/symptom.component';
import { SymptomDetailComponent } from './detail/symptom-detail.component';
import { SymptomUpdateComponent } from './update/symptom-update.component';
import { SymptomDeleteDialogComponent } from './delete/symptom-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [SymptomComponent, SymptomDetailComponent, SymptomUpdateComponent, SymptomDeleteDialogComponent],
  exports: [SymptomDetailComponent, SymptomUpdateComponent, SymptomDeleteDialogComponent],
  entryComponents: [SymptomDeleteDialogComponent],
})
export class SymptomModule {}
