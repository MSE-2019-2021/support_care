import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ContentComponent } from './list/content.component';
import { ContentDetailComponent } from './detail/content-detail.component';
import { ContentUpdateComponent } from './update/content-update.component';
import { ContentDeleteDialogComponent } from './delete/content-delete-dialog.component';

@NgModule({
  imports: [SharedModule, RouterModule],
  declarations: [ContentComponent, ContentDetailComponent, ContentUpdateComponent, ContentDeleteDialogComponent],
  exports: [ContentDetailComponent, ContentUpdateComponent, ContentDeleteDialogComponent],
  entryComponents: [ContentDeleteDialogComponent],
})
export class ContentModule {}
