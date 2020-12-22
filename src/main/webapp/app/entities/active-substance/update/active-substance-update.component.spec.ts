import { ToxicityRate } from 'app/entities/toxicity-rate/toxicity-rate.model';

jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ActiveSubstanceService } from '../service/active-substance.service';
import { ActiveSubstance } from '../active-substance.model';
import { Notice } from 'app/entities/notice/notice.model';
import { Administration } from 'app/entities/administration/administration.model';

import { ActiveSubstanceUpdateComponent } from './active-substance-update.component';

describe('Component Tests', () => {
  describe('ActiveSubstance Management Update Component', () => {
    let comp: ActiveSubstanceUpdateComponent;
    let fixture: ComponentFixture<ActiveSubstanceUpdateComponent>;
    let service: ActiveSubstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ActiveSubstanceUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ActiveSubstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActiveSubstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ActiveSubstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActiveSubstance(123);
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
        const entity = new ActiveSubstance();
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
        const entity = new ActiveSubstance(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);

        expect(comp.isEditing()).toBeTruthy();
      });

      it('should return false when creating component', () => {
        const entity = new ActiveSubstance();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        expect(comp.isEditing()).toBeFalsy();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackNoticeById', () => {
        it('Should return tracked Notice primary key', () => {
          const entity = new Notice(123);
          const trackResult = comp.trackNoticeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackAdministrationById', () => {
        it('Should return tracked Administration primary key', () => {
          const entity = new Administration(123);
          const trackResult = comp.trackAdministrationById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedNotice', () => {
        it('Should return option if no Notice is selected', () => {
          const option = new Notice(123);
          const result = comp.getSelectedNotice(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Notice for according option', () => {
          const option = new Notice(123);
          const selected = new Notice(123);
          const selected2 = new Notice(456);
          const result = comp.getSelectedNotice(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Notice is not selected', () => {
          const option = new Notice(123);
          const selected = new Notice(456);
          const result = comp.getSelectedNotice(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
