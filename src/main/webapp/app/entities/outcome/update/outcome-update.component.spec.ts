jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OutcomeService } from '../service/outcome.service';
import { Outcome } from '../outcome.model';

import { OutcomeUpdateComponent } from './outcome-update.component';

describe('Component Tests', () => {
  describe('Outcome Management Update Component', () => {
    let comp: OutcomeUpdateComponent;
    let fixture: ComponentFixture<OutcomeUpdateComponent>;
    let service: OutcomeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OutcomeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OutcomeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutcomeUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(OutcomeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Outcome(123);
        const files = {} as FileList;
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        comp.files = files;
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity, files);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Outcome();
        const files = {} as FileList;
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        comp.files = files;
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity, files);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
