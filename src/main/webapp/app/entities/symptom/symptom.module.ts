import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { SymptomComponent } from './symptom.component';
import { SymptomDetailComponent } from './symptom-detail.component';
import { SymptomUpdateComponent } from './symptom-update.component';
import { SymptomDeleteDialogComponent } from './symptom-delete-dialog.component';
import { FilterPipe } from './symptom.filter.pipe';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [SymptomComponent, SymptomDetailComponent, SymptomUpdateComponent, SymptomDeleteDialogComponent, FilterPipe],
  exports: [SymptomDetailComponent, SymptomUpdateComponent, SymptomDeleteDialogComponent],
  entryComponents: [SymptomDeleteDialogComponent],
})
export class SymptomModule {}
