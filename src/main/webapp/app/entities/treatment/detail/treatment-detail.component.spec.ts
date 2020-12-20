import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Treatment } from '../treatment.model';

import { TreatmentDetailComponent } from './treatment-detail.component';

describe('Component Tests', () => {
  describe('Treatment Management Detail Component', () => {
    let comp: TreatmentDetailComponent;
    let fixture: ComponentFixture<TreatmentDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TreatmentDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ treatment: new Treatment(123) }) },
          },
        ],
      })
        .overrideTemplate(TreatmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TreatmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load treatment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.treatment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
