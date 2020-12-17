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

    describe('Tracking relationships identifiers', () => {
      describe('trackTherapeuticRegimeById', () => {
        it('Should return tracked TherapeuticRegime primary key', () => {
          const entity = new TherapeuticRegime(123);
          const trackResult = comp.trackTherapeuticRegimeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackOutcomeById', () => {
        it('Should return tracked Outcome primary key', () => {
          const entity = new Outcome(123);
          const trackResult = comp.trackOutcomeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedTherapeuticRegime', () => {
        it('Should return option if no TherapeuticRegime is selected', () => {
          const option = new TherapeuticRegime(123);
          const result = comp.getSelectedTherapeuticRegime(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected TherapeuticRegime for according option', () => {
          const option = new TherapeuticRegime(123);
          const selected = new TherapeuticRegime(123);
          const selected2 = new TherapeuticRegime(456);
          const result = comp.getSelectedTherapeuticRegime(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this TherapeuticRegime is not selected', () => {
          const option = new TherapeuticRegime(123);
          const selected = new TherapeuticRegime(456);
          const result = comp.getSelectedTherapeuticRegime(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });

      describe('getSelectedOutcome', () => {
        it('Should return option if no Outcome is selected', () => {
          const option = new Outcome(123);
          const result = comp.getSelectedOutcome(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Outcome for according option', () => {
          const option = new Outcome(123);
          const selected = new Outcome(123);
          const selected2 = new Outcome(456);
          const result = comp.getSelectedOutcome(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Outcome is not selected', () => {
          const option = new Outcome(123);
          const selected = new Outcome(456);
          const result = comp.getSelectedOutcome(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
