jest.mock('ng-jhipster');
jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DrugDeleteDialogComponent } from 'app/entities/drug/drug-delete-dialog.component';
import { DrugService } from 'app/entities/drug/drug.service';

describe('Component Tests', () => {
  describe('Drug Management Delete Component', () => {
    let comp: DrugDeleteDialogComponent;
    let fixture: ComponentFixture<DrugDeleteDialogComponent>;
    let service: DrugService;
    let mockEventManager: JhiEventManager;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DrugDeleteDialogComponent],
        providers: [NgbActiveModal, JhiEventManager],
      })
        .overrideTemplate(DrugDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrugDeleteDialogComponent);
      comp = fixture.componentInstance;
      comp.eventName = 'myEvent';
      service = TestBed.inject(DrugService);
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
          expect(mockEventManager.broadcast).toHaveBeenCalled();
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
