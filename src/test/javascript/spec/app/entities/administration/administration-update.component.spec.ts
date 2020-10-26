import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SupportivecareTestModule } from '../../../test.module';
import { AdministrationUpdateComponent } from 'app/entities/administration/administration-update.component';
import { AdministrationService } from 'app/entities/administration/administration.service';
import { Administration } from 'app/shared/model/administration.model';

describe('Component Tests', () => {
  describe('Administration Management Update Component', () => {
    let comp: AdministrationUpdateComponent;
    let fixture: ComponentFixture<AdministrationUpdateComponent>;
    let service: AdministrationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SupportivecareTestModule],
        declarations: [AdministrationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AdministrationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdministrationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdministrationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Administration(123);
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
        const entity = new Administration();
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
  });
});
