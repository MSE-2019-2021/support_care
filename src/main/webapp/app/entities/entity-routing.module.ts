import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'administration',
        data: {
          authorities: [Authority.ADMIN],
          pageTitle: 'supportivecareApp.administration.home.title',
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./administration/administration-routing.module').then(m => m.AdministrationRoutingModule),
      },
      {
        path: 'notice',
        data: { pageTitle: 'supportivecareApp.notice.home.title' },
        loadChildren: () => import('./notice/notice-routing.module').then(m => m.NoticeRoutingModule),
      },
      {
        path: 'drug',
        data: { pageTitle: 'supportivecareApp.drug.home.title' },
        loadChildren: () => import('./drug/drug-routing.module').then(m => m.DrugRoutingModule),
      },
      {
        path: 'treatment',
        data: {
          authorities: [Authority.ADMIN],
          pageTitle: 'supportivecareApp.treatment.home.title',
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./treatment/treatment-routing.module').then(m => m.TreatmentRoutingModule),
      },
      {
        path: 'therapeutic-regime',
        data: { pageTitle: 'supportivecareApp.therapeuticRegime.home.title' },
        loadChildren: () => import('./therapeutic-regime/therapeutic-regime-routing.module').then(m => m.TherapeuticRegimeRoutingModule),
      },
      {
        path: 'document',
        data: { pageTitle: 'supportivecareApp.document.home.title' },
        loadChildren: () => import('./document/document-routing.module').then(m => m.DocumentRoutingModule),
      },
      {
        path: 'content',
        data: { pageTitle: 'supportivecareApp.content.home.title' },
        loadChildren: () => import('./content/content-routing.module').then(m => m.ContentRoutingModule),
      },
      {
        path: 'outcome',
        data: { pageTitle: 'supportivecareApp.outcome.home.title' },
        loadChildren: () => import('./outcome/outcome-routing.module').then(m => m.OutcomeRoutingModule),
      },
      {
        path: 'toxicity-rate',
        data: { pageTitle: 'supportivecareApp.toxicityRate.home.title' },
        loadChildren: () => import('./toxicity-rate/toxicity-rate-routing.module').then(m => m.ToxicityRateRoutingModule),
      },
      {
        path: 'symptom',
        data: { pageTitle: 'supportivecareApp.symptom.home.title' },
        loadChildren: () => import('./symptom/symptom-routing.module').then(m => m.SymptomRoutingModule),
      },
      {
        path: 'feedback',
        data: {
          authorities: [Authority.USER],
          pageTitle: 'supportivecareApp.feedback.home.title',
        },
        loadChildren: () => import('./feedback/feedback-routing.module').then(m => m.FeedbackRoutingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
