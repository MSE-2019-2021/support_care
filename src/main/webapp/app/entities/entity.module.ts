import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'administration',
        loadChildren: () => import('./administration/administration.module').then(m => m.SupportivecareAdministrationModule),
      },
      {
        path: 'notice',
        loadChildren: () => import('./notice/notice.module').then(m => m.SupportivecareNoticeModule),
      },
      {
        path: 'drug',
        loadChildren: () => import('./drug/drug.module').then(m => m.SupportivecareDrugModule),
      },
      {
        path: 'treatment',
        loadChildren: () => import('./treatment/treatment.module').then(m => m.SupportivecareTreatmentModule),
      },
      {
        path: 'therapeutic-regime',
        loadChildren: () => import('./therapeutic-regime/therapeutic-regime.module').then(m => m.SupportivecareTherapeuticRegimeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SupportivecareEntityModule {}
