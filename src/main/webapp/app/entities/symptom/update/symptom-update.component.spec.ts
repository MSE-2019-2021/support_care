import { ToxicityRate } from 'app/entities/toxicity-rate/toxicity-rate.model';

jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SymptomService } from '../service/symptom.service';
import { Symptom } from '../symptom.model';
import { TherapeuticRegime } from 'app/entities/therapeutic-regime/therapeutic-regime.model';
import { Outcome } from 'app/entities/outcome/outcome.model';

import { SymptomUpdateComponent } from './symptom-update.component';

describe('Component Tests', () => {
  describe('Symptom Management Update Component', () => {
    let comp: SymptomUpdateComponent;
    let fixture: ComponentFixture<SymptomUpdateComponent>;
    let service: SymptomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SymptomUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SymptomUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SymptomUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(SymptomService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Symptom(123);
        entity.toxicityRates = [];
        entity.outcomes = [];
        entity.therapeuticRegimes = [];
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Symptom();
        entity.toxicityRates = [];
        entity.outcomes = [];
        entity.therapeuticRegimes = [];
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });

    describe('is editing', () => {
      it('should return true when editing component', () => {
        const entity = new Symptom(123);
        entity.toxicityRates = [];
        entity.outcomes = [];
        entity.therapeuticRegimes = [];
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);

        expect(comp.isEditing()).toBeTruthy();
      });

      it('should return false when creating component', () => {
        const entity = new Symptom();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        expect(comp.isEditing()).toBeFalsy();
      });
    });

    describe('Handle toxicity rates Form Array', () => {
      it('Should add and delete a toxity rate from the form array', () => {
        const toxicityRate = new ToxicityRate(111, 'toxicityRate', '', '', '', '', '');

        comp.addToxicityRate(toxicityRate);
        comp.deleteToxicityRate(0);

        expect(comp.getToxicityRates().length).toBe(0);
      });
      it('Should add a new toxicity rate to the form array', () => {
        comp.addToxicityRate();

        expect(comp.getToxicityRates().length).toBe(1);
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedOutcome', () => {
        it('Should return no option if no outcome is available', () => {
          const result = comp.getSelectedOutcome([]);
          expect(result).toEqual([]);
        });

        it('Should return option if outcome is available', () => {
          const option = new Outcome(123, 'name', 'description');
          const list = [option];
          const result = comp.getSelectedOutcome(list);
          expect(result).toEqual([{ id: 123, text: 'name' }]);
        });
      });

      describe('getSelectedTherapeuticRegimes', () => {
        it('Should return no option if no therapeutic regime is available', () => {
          const result = comp.getSelectedTherapeuticRegime([]);
          expect(result).toEqual([]);
        });

        it('Should return option if therapeutic regime is available', () => {
          const option = new TherapeuticRegime(123, 'name', 'acronym');
          const list = [option];
          const result = comp.getSelectedTherapeuticRegime(list);
          expect(result).toEqual([{ id: 123, text: 'acronym' }]);
        });
      });
    });
  });
});
