jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { TherapeuticRegimeService } from '../service/therapeutic-regime.service';
import { TherapeuticRegime } from '../therapeutic-regime.model';
import { ActiveSubstance, IActiveSubstance } from 'app/entities/active-substance/active-substance.model';
import { Treatment } from 'app/entities/treatment/treatment.model';

import { TherapeuticRegimeUpdateComponent } from './therapeutic-regime-update.component';
import { TherapeuticRegimeCancelDialogComponent } from '../cancel/therapeutic-regime-cancel-dialog.component';

export class MockNgbModalRef {
  componentInstance = {
    prompt: undefined,
    title: undefined,
  };
  result: Promise<any> = new Promise(resolve => resolve(true));
}

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Update Component', () => {
    let comp: TherapeuticRegimeUpdateComponent;
    let fixture: ComponentFixture<TherapeuticRegimeUpdateComponent>;
    let service: TherapeuticRegimeService;
    let modalService: NgbModal;
    let mockModalRef: MockNgbModalRef;

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
      service = TestBed.inject(TherapeuticRegimeService);
      modalService = TestBed.inject(NgbModal);
      mockModalRef = new MockNgbModalRef();
    });

    /*
    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TherapeuticRegime(123);
        const activeSubstance = new ActiveSubstance(12, 'name', 'dosage', 'form');
        entity.activeSubstances = [activeSubstance];
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        const translateResult = comp.getSelectedActiveSubstance(entity.activeSubstances);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(mockRouter.navigate).toBeCalledWith(['/therapeutic-regime', 123, 'view']);
        expect(comp.isSaving).toEqual(false);
        expect(entity).toEqual(jasmine.objectContaining({ id: 123 }));
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TherapeuticRegime(123);
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        entity.activeSubstances = [];
        comp.updateForm(entity);
        comp.getSelectedActiveSubstance([]);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(comp.activeSubstances).toEqual([]);
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(mockRouter.navigate).toBeCalledWith(['/therapeutic-regime', 123, 'view']);
        expect(comp.isSaving).toEqual(false);
      }));
    });
    */

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
