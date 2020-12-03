import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SymptomDetailComponent } from 'app/entities/symptom/symptom-detail.component';
import {ISymptom, Symptom} from 'app/shared/model/symptom.model';
import {SymptomService} from "app/entities/symptom/symptom.service";

describe('Component Tests', () => {
  describe('Symptom Management Detail Component', () => {
    let comp: SymptomDetailComponent;
    let fixture: ComponentFixture<SymptomDetailComponent>;
    let service: SymptomService;
    let symptom: ISymptom;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SymptomDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ symptom: new Symptom(123) }) },
          },
        ],
      })
        .overrideTemplate(SymptomDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomDetailComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(SymptomService);
      // symptom = TestBed.inject(symptom);
    });

    describe('OnInit', () => {
      it('Should load symptom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.symptom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('ngOnDestroy', () => {
      it('Should destroy symptom', () => {
        // WHEN
        comp.ngOnDestroy();

        // THEN
        expect(comp.symptom).toEqual(jasmine.objectContaining(null));
      });
    });

    describe('registerChangeInSymptom', () => {
      it('Should register changes in symptom', () => {
        // WHEN
        comp.registerChangeInSymptom();

        // THEN
        expect(comp.symptom).toEqual(jasmine.objectContaining(null));
      });
    });

    describe('previousState', () => {
      it('Should go back', () => {
        // WHEN
        comp.previousState();

        // THEN
        expect(comp.symptom).toEqual(jasmine.objectContaining(null));
      });
    });

    describe('delete', () => {
      it('Should delete symptom', () => {
        // WHEN
         comp.delete(symptom);

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(comp.symptom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
