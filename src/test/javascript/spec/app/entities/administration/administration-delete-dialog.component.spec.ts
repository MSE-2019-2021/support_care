jest.mock('ng-jhipster');
jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AdministrationDeleteDialogComponent } from 'app/entities/administration/administration-delete-dialog.component';
import { AdministrationService } from 'app/entities/administration/administration.service';

describe('Component Tests', () => {
  describe('Administration Management Delete Component', () => {
    let comp: AdministrationDeleteDialogComponent;
    let fixture: ComponentFixture<AdministrationDeleteDialogComponent>;
    let service: AdministrationService;
    let mockEventManager: JhiEventManager;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AdministrationDeleteDialogComponent],
        providers: [NgbActiveModal, JhiEventManager],
      })
        .overrideTemplate(AdministrationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdministrationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AdministrationService);
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
