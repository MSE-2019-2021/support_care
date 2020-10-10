import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SupportcareTestModule } from '../../../test.module';
import { GuideDetailComponent } from 'app/entities/guide/guide-detail.component';
import { Guide } from 'app/shared/model/guide.model';

describe('Component Tests', () => {
  describe('Guide Management Detail Component', () => {
    let comp: GuideDetailComponent;
    let fixture: ComponentFixture<GuideDetailComponent>;
    const route = ({ data: of({ guide: new Guide(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SupportcareTestModule],
        declarations: [GuideDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GuideDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GuideDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load guide on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.guide).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
