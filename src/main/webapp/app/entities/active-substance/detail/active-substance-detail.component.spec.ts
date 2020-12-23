import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ActiveSubstance } from '../active-substance.model';

import { ActiveSubstanceDetailComponent } from './active-substance-detail.component';

describe('Component Tests', () => {
  describe('ActiveSubstance Management Detail Component', () => {
    let comp: ActiveSubstanceDetailComponent;
    let fixture: ComponentFixture<ActiveSubstanceDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ActiveSubstanceDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ activeSubstance: new ActiveSubstance(123) }) },
          },
        ],
      })
        .overrideTemplate(ActiveSubstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActiveSubstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load activeSubstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.activeSubstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('previousState', () => {
      it('Should go back', () => {
        // WHEN
        comp.previousState();

        // THEN
        expect(comp.activeSubstance).toEqual(jasmine.objectContaining(null));
      });
    });
  });
});
