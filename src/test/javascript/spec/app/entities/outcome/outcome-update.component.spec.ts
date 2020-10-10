import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SupportcareTestModule } from '../../../test.module';
import { OutcomeUpdateComponent } from 'app/entities/outcome/outcome-update.component';
import { OutcomeService } from 'app/entities/outcome/outcome.service';
import { Outcome } from 'app/shared/model/outcome.model';

describe('Component Tests', () => {
  describe('Outcome Management Update Component', () => {
    let comp: OutcomeUpdateComponent;
    let fixture: ComponentFixture<OutcomeUpdateComponent>;
    let service: OutcomeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SupportcareTestModule],
        declarations: [OutcomeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OutcomeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutcomeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutcomeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Outcome(123);
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
        const entity = new Outcome();
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
