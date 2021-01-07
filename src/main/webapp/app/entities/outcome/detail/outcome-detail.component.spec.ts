import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { OutcomeDetailComponent } from 'app/entities/outcome/detail/outcome-detail.component';
import { Outcome } from 'app/entities/outcome/outcome.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OutcomeDeleteDialogComponent } from 'app/entities/outcome/delete/outcome-delete-dialog.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

export class MockNgbModalRef {
  componentInstance = {
    prompt: undefined,
    title: undefined,
  };
  result: Promise<any> = new Promise(resolve => resolve(true));
}

describe('Component Tests', () => {
  describe('Outcome Management Detail Component', () => {
    let comp: OutcomeDetailComponent;
    let fixture: ComponentFixture<OutcomeDetailComponent>;
    let modalService: NgbModal;
    let mockModalRef: MockNgbModalRef;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OutcomeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ outcome: new Outcome(123) }) },
          },
        ],
      })
        .overrideTemplate(OutcomeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutcomeDetailComponent);
      comp = fixture.componentInstance;
      modalService = TestBed.inject(NgbModal);
      mockModalRef = new MockNgbModalRef();
    });

    describe('OnInit', () => {
      it('Should load outcome on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.outcome).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('delete', () => {
      it('should open modal when clicking on delete button', fakeAsync(() => {
        // GIVEN
        const outcome = new Outcome(123);
        spyOn(modalService, 'open').and.returnValue(mockModalRef);

        // WHEN
        comp.delete(outcome);

        // THEN
        expect(modalService.open).toHaveBeenCalledWith(OutcomeDeleteDialogComponent, {
          centered: true,
          size: 'lg',
          backdrop: 'static',
        });
      }));
    });
  });
});
