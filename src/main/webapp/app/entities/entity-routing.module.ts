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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
