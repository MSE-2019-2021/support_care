import { NgModule } from '@angular/core';

import { FeedbackEntityListHeaderComponent } from 'app/entities/feedback/entity-list-header/feedback-entity-list-header.component';
import { FeedbackEntityListCardComponent } from 'app/entities/feedback/entity-list-card/feedback-entity-list-card.component';

@NgModule({
  declarations: [FeedbackEntityListHeaderComponent, FeedbackEntityListCardComponent],
  exports: [FeedbackEntityListHeaderComponent, FeedbackEntityListCardComponent],
})
export class FeedbackEntityListModule {}
