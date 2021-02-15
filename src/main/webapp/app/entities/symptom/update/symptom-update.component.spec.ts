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
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SymptomCancelDialogComponent } from 'app/entities/symptom/cancel/symptom-cancel-dialog.component';

export class MockNgbModalRef {
  componentInstance = {
    prompt: undefined,
    title: undefined,
  };
  result: Promise<any> = new Promise(resolve => resolve(true));
}

describe('Component Tests', () => {
  describe('Symptom Management Update Component', () => {
    let comp: SymptomUpdateComponent;
    let fixture: ComponentFixture<SymptomUpdateComponent>;
    let service: SymptomService;
    let modalService: NgbModal;
    let mockModalRef: MockNgbModalRef;

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
      modalService = TestBed.inject(NgbModal);
      mockModalRef = new MockNgbModalRef();
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
          const option = new TherapeuticRegime(123, 'name');
          const list = [option];
          const result = comp.getSelectedTherapeuticRegime(list);
          expect(result).toEqual([{ id: 123, text: 'name' }]);
        });
      });
    });
    describe('cancel', () => {
      it('should open modal when clicking on cancel button', fakeAsync(() => {
        // GIVEN
        spyOn(modalService, 'open').and.returnValue(mockModalRef);

        // WHEN
        comp.cancel();

        // THEN
        expect(modalService.open).toHaveBeenCalledWith(SymptomCancelDialogComponent, {
          centered: true,
          size: 'lg',
          backdrop: 'static',
        });
      }));
    });

    describe('Dropdown actions', () => {
      it('Should search therapeutic regimes', () => {
        // WHEN
        comp.searchingTR('');

        // THEN
        comp.resetTR();
        expect(comp.page).toEqual(0);
      });

      it('Should search outcomes', () => {
        // WHEN
        comp.searchingO('');

        // THEN
        comp.resetO();
        expect(comp.page).toEqual(0);
      });

      it('Should load page 0 for outcomes', () => {
        // WHEN
        comp.loadPageO(0);

        // THEN
        expect(comp.page).toEqual(0);
      });

      it('Should load page 0 for therapeutic regimes', () => {
        // WHEN
        comp.loadPageTR(0);

        // THEN
        expect(comp.page).toEqual(0);
      });

      it('Should filter query therapeutic regimes or outcomes by name', () => {
        // WHEN
        comp.searchName = 'name';
        comp.getCriteria();

        const result = comp.sort();

        // THEN
        expect(comp.searchName).toEqual('name');
        expect(result).toEqual(['name,asc', 'id']);
      });

      it('should calculate the sort attribute for a name', () => {
        // WHEN
        const result = comp.sort();

        // THEN
        expect(result).toEqual(['name,asc', 'id']);
      });
    });
  });
});
