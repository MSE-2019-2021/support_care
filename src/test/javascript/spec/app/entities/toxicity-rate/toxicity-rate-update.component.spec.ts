jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ToxicityRateUpdateComponent } from 'app/entities/toxicity-rate/toxicity-rate-update.component';
import { ToxicityRateService } from 'app/entities/toxicity-rate/toxicity-rate.service';
import { ToxicityRate } from 'app/shared/model/toxicity-rate.model';

describe('Component Tests', () => {
  describe('ToxicityRate Management Update Component', () => {
    let comp: ToxicityRateUpdateComponent;
    let fixture: ComponentFixture<ToxicityRateUpdateComponent>;
    let service: ToxicityRateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ToxicityRateUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ToxicityRateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ToxicityRateUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ToxicityRateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ToxicityRate(123);
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
        const entity = new ToxicityRate();
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