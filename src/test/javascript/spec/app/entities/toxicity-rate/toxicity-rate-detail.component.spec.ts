import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ToxicityRateDetailComponent } from 'app/entities/toxicity-rate/toxicity-rate-detail.component';
import { ToxicityRate } from 'app/shared/model/toxicity-rate.model';

describe('Component Tests', () => {
  describe('ToxicityRate Management Detail Component', () => {
    let comp: ToxicityRateDetailComponent;
    let fixture: ComponentFixture<ToxicityRateDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ToxicityRateDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ toxicityRate: new ToxicityRate(123) }) },
          },
        ],
      })
        .overrideTemplate(ToxicityRateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ToxicityRateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load toxicityRate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.toxicityRate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
