import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'notice',
        data: { pageTitle: 'supportivecareApp.notice.home.title' },
        loadChildren: () => import('./notice/notice.module').then(m => m.NoticeModule),
      },
      {
        path: 'active-substance',
        data: { pageTitle: 'supportivecareApp.activeSubstance.home.title' },
        loadChildren: () => import('./active-substance/active-substance.module').then(m => m.ActiveSubstanceModule),
      },
      {
        path: 'therapeutic-regime',
        data: { pageTitle: 'supportivecareApp.therapeuticRegime.home.title' },
        loadChildren: () => import('./therapeutic-regime/therapeutic-regime.module').then(m => m.TherapeuticRegimeModule),
      },
      {
        path: 'outcome',
        data: { pageTitle: 'supportivecareApp.outcome.home.title' },
        loadChildren: () => import('./outcome/outcome.module').then(m => m.OutcomeModule),
      },
      {
        path: 'toxicity-rate',
        data: { pageTitle: 'supportivecareApp.toxicityRate.home.title' },
        loadChildren: () => import('./toxicity-rate/toxicity-rate.module').then(m => m.ToxicityRateModule),
      },
      {
        path: 'symptom',
        data: { pageTitle: 'supportivecareApp.symptom.home.title' },
        loadChildren: () => import('./symptom/symptom.module').then(m => m.SymptomModule),
      },
      {
        path: 'feedback',
        data: { pageTitle: 'supportivecareApp.feedback.home.title' },
        loadChildren: () => import('./feedback/feedback.module').then(m => m.FeedbackModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
