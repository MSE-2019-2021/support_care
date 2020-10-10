import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'drug',
        loadChildren: () => import('./drug/drug.module').then(m => m.SupportcareDrugModule),
      },
      {
        path: 'therapeutic-regime',
        loadChildren: () => import('./therapeutic-regime/therapeutic-regime.module').then(m => m.SupportcareTherapeuticRegimeModule),
      },
      {
        path: 'protocol',
        loadChildren: () => import('./protocol/protocol.module').then(m => m.SupportcareProtocolModule),
      },
      {
        path: 'outcome',
        loadChildren: () => import('./outcome/outcome.module').then(m => m.SupportcareOutcomeModule),
      },
      {
        path: 'guide',
        loadChildren: () => import('./guide/guide.module').then(m => m.SupportcareGuideModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SupportcareEntityModule {}
