import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SupportcareTestModule } from '../../../test.module';
import { GuideUpdateComponent } from 'app/entities/guide/guide-update.component';
import { GuideService } from 'app/entities/guide/guide.service';
import { Guide } from 'app/shared/model/guide.model';

describe('Component Tests', () => {
  describe('Guide Management Update Component', () => {
    let comp: GuideUpdateComponent;
    let fixture: ComponentFixture<GuideUpdateComponent>;
    let service: GuideService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SupportcareTestModule],
        declarations: [GuideUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GuideUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GuideUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GuideService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Guide(123);
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
        const entity = new Guide();
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
