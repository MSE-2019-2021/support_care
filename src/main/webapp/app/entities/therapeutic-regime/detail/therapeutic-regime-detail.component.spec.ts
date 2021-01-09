import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TherapeuticRegime } from '../therapeutic-regime.model';

import { TherapeuticRegimeDetailComponent } from './therapeutic-regime-detail.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';
import { Feedback } from 'app/entities/feedback/feedback.model';
import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';

describe('Component Tests', () => {
  describe('TherapeuticRegime Management Detail Component', () => {
    let mockActiveModal: NgbActiveModal;
    let comp: TherapeuticRegimeDetailComponent;
    let fixture: ComponentFixture<TherapeuticRegimeDetailComponent>;
    let service: FeedbackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TherapeuticRegimeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ therapeuticRegime: new TherapeuticRegime(123) }) },
          },
        ],
      })
        .overrideTemplate(TherapeuticRegimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TherapeuticRegimeDetailComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FeedbackService);
    });

    describe('OnInit', () => {
      it('Should load therapeuticRegime on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.therapeuticRegime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('previousState', () => {
      it('Should go back', () => {
        // WHEN
        comp.previousState();

        // THEN
        expect(comp.therapeuticRegime).toEqual(jasmine.objectContaining(null));
      });
    });
    /*
    describe('manageFeedback', () => {
      it('Should enter in the managefeedback service', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          const feedback = new Feedback();
          feedback.entityName = EntityFeedback.THERAPEUTIC_REGIME;
          feedback.entityId = comp.therapeuticRegime?.id;
          feedback.thumb = true;

          spyOn(service, 'manageFeedbackFromEntity').and.returnValue(of({}));

          // WHEN
          comp.manageFeedback(true);
          tick();

          // THEN
          expect(service.manageFeedbackFromEntity(feedback)).toHaveBeenCalled();
        })
      ));
    });
*/
  });
});
