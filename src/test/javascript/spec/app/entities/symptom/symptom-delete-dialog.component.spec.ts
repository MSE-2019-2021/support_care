jest.mock('ng-jhipster');
jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SymptomDeleteDialogComponent } from 'app/entities/symptom/symptom-delete-dialog.component';
import { SymptomService } from 'app/entities/symptom/symptom.service';

describe('Component Tests', () => {
  describe('Symptom Management Delete Component', () => {
    let comp: SymptomDeleteDialogComponent;
    let fixture: ComponentFixture<SymptomDeleteDialogComponent>;
    let service: SymptomService;
    let mockEventManager: JhiEventManager;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SymptomDeleteDialogComponent],
        providers: [NgbActiveModal, JhiEventManager],
      })
        .overrideTemplate(SymptomDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(SymptomService);
      mockEventManager = TestBed.inject(JhiEventManager);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalled();
        })
      ));

     it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
