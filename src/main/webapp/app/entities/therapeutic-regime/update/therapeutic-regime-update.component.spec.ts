jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ActiveSubstance } from 'app/entities/active-substance/active-substance.model';
import { Treatment } from 'app/entities/treatment/treatment.model';

import { TherapeuticRegimeUpdateComponent } from './therapeutic-regime-update.component';
import { TherapeuticRegimeCancelDialogComponent } from '../cancel/therapeutic-regime-cancel-dialog.component';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Update Component', () => {
    let comp: TherapeuticRegimeUpdateComponent;
    let fixture: ComponentFixture<TherapeuticRegimeUpdateComponent>;

    const mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TherapeuticRegimeUpdateComponent, TherapeuticRegimeCancelDialogComponent],
        providers: [FormBuilder, ActivatedRoute, Router, { provide: Router, useValue: mockRouter }],
      })
        .overrideTemplate(TherapeuticRegimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TherapeuticRegimeUpdateComponent);
      comp = fixture.componentInstance;
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTreatmentById', () => {
        it('Should return tracked Treatment primary key', () => {
          const entity = new Treatment(123);
          const trackResult = comp.trackTreatmentById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedActiveSubstance', () => {
        it('Should return no option if no ActiveSubstance is available', () => {
          const result = comp.getSelectedActiveSubstance([]);
          expect(result).toEqual([]);
        });

        it('Should return option if ActiveSubstance is available', () => {
          const option = new ActiveSubstance(123, 'name', 'dosage', 'form');
          const list = [option];
          const result = comp.getSelectedActiveSubstance(list);
          expect(result).toEqual([{ id: 123, text: 'name - dosage (form)' }]);
        });
      });
    });
  });
});
