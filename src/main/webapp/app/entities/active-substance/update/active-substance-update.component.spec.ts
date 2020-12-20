jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ActiveSubstanceService } from '../service/active-substance.service';
import { ActiveSubstance } from '../active-substance.model';
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

    describe('Tracking relationships identifiers', () => {
      describe('trackAdministrationById', () => {
        it('Should return tracked Administration primary key', () => {
          const entity = new Administration(123);
          const trackResult = comp.trackAdministrationById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
