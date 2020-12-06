import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { DocumentComponent } from './list/document.component';
import { DocumentDetailComponent } from './detail/document-detail.component';
import { DocumentUpdateComponent } from './update/document-update.component';
import { DocumentDeleteDialogComponent } from './delete/document-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [DocumentComponent, DocumentDetailComponent, DocumentUpdateComponent, DocumentDeleteDialogComponent],
  exports: [DocumentDetailComponent, DocumentUpdateComponent, DocumentDeleteDialogComponent],
  entryComponents: [DocumentDeleteDialogComponent],
})
export class DocumentModule {}
