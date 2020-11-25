jest.mock('ng-jhipster');
jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Location } from '@angular/common';

import { TherapeuticRegimeCancelDialogComponent } from 'app/entities/therapeutic-regime/therapeutic-regime-cancel-dialog.component';
import { TherapeuticRegimeService } from 'app/entities/therapeutic-regime/therapeutic-regime.service';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Cancel Component', () => {
    let comp: TherapeuticRegimeCancelDialogComponent;
    let fixture: ComponentFixture<TherapeuticRegimeCancelDialogComponent>;
    //let service: TherapeuticRegimeService;
    let mockEventManager: JhiEventManager;
    let mockActiveModal: NgbActiveModal;
    let mockLocation: Location;


    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TherapeuticRegimeCancelDialogComponent],
        providers: [NgbActiveModal, JhiEventManager],
      })
        .overrideTemplate(TherapeuticRegimeCancelDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TherapeuticRegimeCancelDialogComponent);
      comp = fixture.componentInstance;
      comp.eventName = 'myEvent';
      service = TestBed.inject(TherapeuticRegimeService);
      mockEventManager = TestBed.inject(JhiEventManager);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmCancel', () => {
      it('Should call cancel service on confirmCancel', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'cancel').and.returnValue(of({}));

          // WHEN
          comp.confirmCancel(123);
          tick();

          // THEN
          expect(service.cancel).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalled();
          expect(mockEventManager.broadcast).toHaveBeenCalled();
        })
      ));

      it('Should not call cancel service on clear', () => {
        // GIVEN
        spyOn(service, 'cancel');

        // WHEN
        comp.();

        // THEN
        expect(service.cancel).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
