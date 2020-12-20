jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TherapeuticRegimeService } from '../service/therapeutic-regime.service';
import { TherapeuticRegime } from '../therapeutic-regime.model';
import { ActiveSubstance } from 'app/entities/active-substance/active-substance.model';
import { Treatment } from 'app/entities/treatment/treatment.model';

import { TherapeuticRegimeUpdateComponent } from './therapeutic-regime-update.component';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Update Component', () => {
    let comp: TherapeuticRegimeUpdateComponent;
    let fixture: ComponentFixture<TherapeuticRegimeUpdateComponent>;
    let service: TherapeuticRegimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TherapeuticRegimeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TherapeuticRegimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TherapeuticRegimeUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TherapeuticRegimeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TherapeuticRegime(123);
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
        const entity = new TherapeuticRegime();
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
      describe('trackActiveSubstanceById', () => {
        it('Should return tracked ActiveSubstance primary key', () => {
          const entity = new ActiveSubstance(123);
          const trackResult = comp.trackActiveSubstanceById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

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
        it('Should return option if no ActiveSubstance is selected', () => {
          const option = new ActiveSubstance(123);
          const result = comp.getSelectedActiveSubstance(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected ActiveSubstance for according option', () => {
          const option = new ActiveSubstance(123);
          const selected = new ActiveSubstance(123);
          const selected2 = new ActiveSubstance(456);
          const result = comp.getSelectedActiveSubstance(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this ActiveSubstance is not selected', () => {
          const option = new ActiveSubstance(123);
          const selected = new ActiveSubstance(456);
          const result = comp.getSelectedActiveSubstance(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
