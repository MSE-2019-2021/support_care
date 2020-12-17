import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'administration',
        data: { pageTitle: 'supportivecareApp.administration.home.title' },
        loadChildren: () => import('./administration/route/administration-routing.module').then(m => m.AdministrationRoutingModule),
      },
      {
        path: 'drug',
        data: { pageTitle: 'supportivecareApp.drug.home.title' },
        loadChildren: () => import('./drug/route/drug-routing.module').then(m => m.DrugRoutingModule),
      },
      {
        path: 'treatment',
        data: { pageTitle: 'supportivecareApp.treatment.home.title' },
        loadChildren: () => import('./treatment/route/treatment-routing.module').then(m => m.TreatmentRoutingModule),
      },
      {
        path: 'therapeutic-regime',
        data: { pageTitle: 'supportivecareApp.therapeuticRegime.home.title' },
        loadChildren: () =>
          import('./therapeutic-regime/route/therapeutic-regime-routing.module').then(m => m.TherapeuticRegimeRoutingModule),
      },
      {
        path: 'outcome',
        data: { pageTitle: 'supportivecareApp.outcome.home.title' },
        loadChildren: () => import('./outcome/route/outcome-routing.module').then(m => m.OutcomeRoutingModule),
      },
      {
        path: 'symptom',
        data: { pageTitle: 'supportivecareApp.symptom.home.title' },
        loadChildren: () => import('./symptom/route/symptom-routing.module').then(m => m.SymptomRoutingModule),
      },
      {
        path: 'feedback',
        data: { pageTitle: 'supportivecareApp.feedback.home.title' },
        loadChildren: () => import('./feedback/route/feedback-routing.module').then(m => m.FeedbackRoutingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
