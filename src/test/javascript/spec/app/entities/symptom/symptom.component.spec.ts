jest.mock('@angular/router');

import {ComponentFixture, getTestBed, TestBed} from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

import { SymptomComponent } from 'app/entities/symptom/symptom.component';
import { SymptomService } from 'app/entities/symptom/symptom.service';
import { Symptom } from 'app/shared/model/symptom.model';

describe('Component Tests', () => {
  describe('Symptom Management Component', () => {
    let comp: SymptomComponent;
    let fixture: ComponentFixture<SymptomComponent>;
    let service: SymptomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SymptomComponent],
        providers: [
          Router,
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                jest.requireActual('@angular/router').convertToParamMap(
                  {},
                  {
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                }
                )
              ),
            },
          },
        ],
      })
        .overrideTemplate(SymptomComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SymptomComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(SymptomService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Symptom(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.symptoms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Symptom(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.symptoms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Symptom(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);
      comp.reset();

      // THEN
      expect(comp.page).toEqual(0);
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.symptoms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for a name', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });

    it('Should search symptom', () => {
      // WHEN
      comp.searching();

      // THEN
      comp.reset();
      expect(comp.page).toEqual(0);
    });

    it('Should filter query symptom by name', () => {
      // WHEN
      comp.searchName = 'name'
      comp.getCriterias();

      const result = comp.sort();

      // THEN
      expect(comp.searchName).toEqual('name');
      expect(result).toEqual(['name,asc', 'id']);
    });
  });
});
