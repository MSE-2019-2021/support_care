import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OutcomeDetailComponent } from 'app/entities/outcome/outcome-detail.component';
import { Outcome } from 'app/shared/model/outcome.model';

describe('Component Tests', () => {
  describe('Outcome Management Detail Component', () => {
    let comp: OutcomeDetailComponent;
    let fixture: ComponentFixture<OutcomeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [OutcomeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ outcome: new Outcome(123) }) },
          },
        ],
      })
        .overrideTemplate(OutcomeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutcomeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load outcome on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.outcome).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
