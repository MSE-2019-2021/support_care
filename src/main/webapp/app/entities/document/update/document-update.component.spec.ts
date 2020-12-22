jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocumentService } from '../service/document.service';
import { Document } from '../document.model';
import { Content } from 'app/entities/content/content.model';
import { Outcome } from 'app/entities/outcome/outcome.model';

import { DocumentUpdateComponent } from './document-update.component';

describe('Component Tests', () => {
  describe('Document Management Update Component', () => {
    let comp: DocumentUpdateComponent;
    let fixture: ComponentFixture<DocumentUpdateComponent>;
    let service: DocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DocumentUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(DocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Document(123);
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
        const entity = new Document();
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
      describe('trackContentById', () => {
        it('Should return tracked Content primary key', () => {
          const entity = new Content(123);
          const trackResult = comp.trackContentById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackOutcomeById', () => {
        it('Should return tracked Outcome primary key', () => {
          const entity = new Outcome(123);
          const trackResult = comp.trackOutcomeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});