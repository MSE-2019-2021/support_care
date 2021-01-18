jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

import { TherapeuticRegimeService } from '../service/therapeutic-regime.service';
import { TherapeuticRegime } from '../therapeutic-regime.model';

import { TherapeuticRegimeComponent } from './therapeutic-regime.component';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Component', () => {
    let comp: TherapeuticRegimeComponent;
    let fixture: ComponentFixture<TherapeuticRegimeComponent>;
    let service: TherapeuticRegimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TherapeuticRegimeComponent],
        providers: [
          Router,
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                jest.requireActual('@angular/router').convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(TherapeuticRegimeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TherapeuticRegimeComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TherapeuticRegimeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TherapeuticRegime(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.therapeuticRegimes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TherapeuticRegime(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.therapeuticRegimes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TherapeuticRegime(123)],
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
      expect(comp.therapeuticRegimes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      comp.searchName = 'name';
      comp.getCriteria();

      const result = comp.sort();

      // THEN
      expect(comp.searchName).toEqual('name');
      expect(result).toEqual(['name,asc', 'id']);
    });
  });
});
