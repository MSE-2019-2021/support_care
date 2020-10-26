import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SupportivecareTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { TherapeuticRegimeDeleteDialogComponent } from 'app/entities/therapeutic-regime/therapeutic-regime-delete-dialog.component';
import { TherapeuticRegimeService } from 'app/entities/therapeutic-regime/therapeutic-regime.service';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Delete Component', () => {
    let comp: TherapeuticRegimeDeleteDialogComponent;
    let fixture: ComponentFixture<TherapeuticRegimeDeleteDialogComponent>;
    let service: TherapeuticRegimeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SupportivecareTestModule],
        declarations: [TherapeuticRegimeDeleteDialogComponent],
      })
        .overrideTemplate(TherapeuticRegimeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TherapeuticRegimeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TherapeuticRegimeService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
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
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
