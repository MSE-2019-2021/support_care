import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SupportivecareTestModule } from '../../../test.module';
import { AdministrationDetailComponent } from 'app/entities/administration/administration-detail.component';
import { Administration } from 'app/shared/model/administration.model';

describe('Component Tests', () => {
  describe('Administration Management Detail Component', () => {
    let comp: AdministrationDetailComponent;
    let fixture: ComponentFixture<AdministrationDetailComponent>;
    const route = ({ data: of({ administration: new Administration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SupportivecareTestModule],
        declarations: [AdministrationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AdministrationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdministrationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load administration on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.administration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
