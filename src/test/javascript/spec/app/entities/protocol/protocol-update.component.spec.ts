import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SupportcareTestModule } from '../../../test.module';
import { ProtocolUpdateComponent } from 'app/entities/protocol/protocol-update.component';
import { ProtocolService } from 'app/entities/protocol/protocol.service';
import { Protocol } from 'app/shared/model/protocol.model';

describe('Component Tests', () => {
  describe('Protocol Management Update Component', () => {
    let comp: ProtocolUpdateComponent;
    let fixture: ComponentFixture<ProtocolUpdateComponent>;
    let service: ProtocolService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SupportcareTestModule],
        declarations: [ProtocolUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProtocolUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProtocolUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProtocolService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Protocol(123);
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
        const entity = new Protocol();
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
