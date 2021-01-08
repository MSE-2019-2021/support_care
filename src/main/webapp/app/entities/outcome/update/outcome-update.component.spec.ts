jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OutcomeService } from '../service/outcome.service';
import { Outcome } from '../outcome.model';
import { Document } from 'app/entities/document/document.model';

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
        entity.documents = [];
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
        entity.documents = [];
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

    it('Should return file input', () => {
      const entity = new Outcome();
      spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
      const targetFiles = ({ target: { files: {} } } as unknown) as Event;
      comp.handleFileInput(targetFiles);
      expect(comp.files).toEqual({});
    });

    describe('Handle document Form Array', () => {
      it('Should add and delete a document from the form array', () => {
        const document = new Document(111);

        comp.addDocument(document);
        comp.removeDocument(0);

        expect(comp.getDocuments().length).toBe(0);
      });
    });

    describe('is editing', () => {
      it('should return true when editing component', () => {
        const entity = new Outcome(123);
        entity.documents = [];
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);

        expect(comp.isEditing()).toBeTruthy();
      });

      it('should return false when creating component', () => {
        const entity = new Outcome();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        expect(comp.isEditing()).toBeFalsy();
      });
    });

    it('should return outcome files', () => {
      const entity = new Outcome(123);
      entity.documents = [];
      const files = {} as FileList;
      comp.files = files;

      expect(comp.getFiles()).toEqual({});
    });
  });
});
