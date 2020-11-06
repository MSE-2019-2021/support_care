import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'administration',
        loadChildren: () => import('./administration/administration-routing.module').then(m => m.AdministrationRoutingModule),
      },
      {
        path: 'notice',
        loadChildren: () => import('./notice/notice-routing.module').then(m => m.NoticeRoutingModule),
      },
      {
        path: 'drug',
        loadChildren: () => import('./drug/drug-routing.module').then(m => m.DrugRoutingModule),
      },
      {
        path: 'treatment',
        loadChildren: () => import('./treatment/treatment-routing.module').then(m => m.TreatmentRoutingModule),
      },
      {
        path: 'therapeutic-regime',
        loadChildren: () => import('./therapeutic-regime/therapeutic-regime-routing.module').then(m => m.TherapeuticRegimeRoutingModule),
      },
      {
        path: 'document',
        loadChildren: () => import('./document/document-routing.module').then(m => m.DocumentRoutingModule),
      },
      {
        path: 'content',
        loadChildren: () => import('./content/content-routing.module').then(m => m.ContentRoutingModule),
      },
      {
        path: 'outcome',
        loadChildren: () => import('./outcome/outcome-routing.module').then(m => m.OutcomeRoutingModule),
      },
      {
        path: 'toxicity-rate',
        loadChildren: () => import('./toxicity-rate/toxicity-rate-routing.module').then(m => m.ToxicityRateRoutingModule),
      },
      {
        path: 'symptom',
        loadChildren: () => import('./symptom/symptom-routing.module').then(m => m.SymptomRoutingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
