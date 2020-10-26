import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SupportivecareTestModule } from '../../../test.module';
import { TherapeuticRegimeDetailComponent } from 'app/entities/therapeutic-regime/therapeutic-regime-detail.component';
import { TherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Detail Component', () => {
    let comp: TherapeuticRegimeDetailComponent;
    let fixture: ComponentFixture<TherapeuticRegimeDetailComponent>;
    const route = ({ data: of({ therapeuticRegime: new TherapeuticRegime(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SupportivecareTestModule],
        declarations: [TherapeuticRegimeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TherapeuticRegimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TherapeuticRegimeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load therapeuticRegime on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.therapeuticRegime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
