import { Drug } from 'app/shared/model/drug.model';

jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

import { TherapeuticRegimeUpdateComponent } from 'app/entities/therapeutic-regime/therapeutic-regime-update.component';
import { TherapeuticRegimeService } from 'app/entities/therapeutic-regime/therapeutic-regime.service';
import { TherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TherapeuticRegimeCancelDialogComponent } from 'app/entities/therapeutic-regime/therapeutic-regime-cancel-dialog.component';

export class MockNgbModalRef {
  componentInstance = {
    prompt: undefined,
    title: undefined,
  };
  result: Promise<any> = new Promise((resolve, reject) => resolve(true));
}

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Update Component', () => {
    let comp: TherapeuticRegimeUpdateComponent;
    let fixture: ComponentFixture<TherapeuticRegimeUpdateComponent>;
    let service: TherapeuticRegimeService;
    let modalService: NgbModal;
    let mockModalRef: MockNgbModalRef;

    const mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TherapeuticRegimeUpdateComponent, TherapeuticRegimeCancelDialogComponent],
        providers: [NgbActiveModal, FormBuilder, ActivatedRoute, Router, { provide: Router, useValue: mockRouter }],
      })
        .overrideTemplate(TherapeuticRegimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TherapeuticRegimeUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TherapeuticRegimeService);
      modalService = TestBed.inject(NgbModal);
      mockModalRef = new MockNgbModalRef();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TherapeuticRegime(123);
        const drug = new Drug(12);
        entity.drugs = [drug];
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        const translateResult = comp.getSelect2Options(entity.drugs);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(comp.dropdownSettings).toEqual({});
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(mockRouter.navigate).toBeCalledWith(['/therapeutic-regime', 123, 'view']);
        expect(translateResult).toEqual([{ id: 12, text: undefined }]);
        expect(comp.isSaving).toEqual(false);
        expect(entity).toEqual(jasmine.objectContaining({ id: 123 }));
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TherapeuticRegime();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        entity.drugs = [];
        comp.updateForm(entity);
        comp.getSelect2Options([]);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(comp.drugs).toEqual([]);
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(mockRouter.navigate).toBeCalledWith(['/therapeutic-regime', 123, 'view']);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should fill dropdown Settings object', fakeAsync(() => {
        // GIVEN
        comp.dropdownSettings = {};
        // WHEN
        comp.ngOnInit();
        tick(); // simulate async

        // THEN
        expect(comp.dropdownSettings).toEqual({
          singleSelection: false,
          idField: 'id',
          textField: 'text',
          selectAllText: 'Select All',
          unSelectAllText: 'UnSelect All',
          itemsShowLimit: 3,
          allowSearchFilter: true,
        });
      }));
    });

    describe('cancel', () => {
      it('should open modal', fakeAsync(() => {
        // GIVEN
        spyOn(modalService, 'open').and.returnValue(mockModalRef);

        // WHEN
        comp.cancel();
        tick(); // simulate async

        // THEN
        expect(modalService.open).toHaveBeenCalledWith(TherapeuticRegimeCancelDialogComponent, {
          centered: true,
          size: 'lg',
          backdrop: 'static',
        });
      }));
    });
  });
});
