jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NoticeService } from '../service/notice.service';
import { Notice } from '../notice.model';
import { ActiveSubstance } from 'app/entities/active-substance/active-substance.model';

import { NoticeUpdateComponent } from './notice-update.component';

describe('Component Tests', () => {
  describe('Notice Management Update Component', () => {
    let comp: NoticeUpdateComponent;
    let fixture: ComponentFixture<NoticeUpdateComponent>;
    let service: NoticeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NoticeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NoticeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NoticeUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NoticeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Notice(123);
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
        const entity = new Notice();
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
      describe('trackActiveSubstanceById', () => {
        it('Should return tracked ActiveSubstance primary key', () => {
          const entity = new ActiveSubstance(123);
          const trackResult = comp.trackActiveSubstanceById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
