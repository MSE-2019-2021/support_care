import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

import { FeedbackService } from '../service/feedback.service';
import { Feedback } from '../feedback.model';

import { FeedbackComponent } from './feedback.component';

jest.mock('@angular/router');

describe('Component Tests', () => {
  describe('Feedback Management Component', () => {
    let comp: FeedbackComponent;
    let fixture: ComponentFixture<FeedbackComponent>;
    let service: FeedbackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FeedbackComponent],
        providers: [
          FormBuilder,
          Router,
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: ['solved,asc', 'createdDate,asc', 'id'],
              }),
              queryParamMap: of(
                jest.requireActual('@angular/router').convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: ['solved,desc', 'createdDate,desc', 'id'],
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(FeedbackComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeedbackComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FeedbackService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Feedback(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.feedbacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Feedback(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.feedbacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should return id', () => {
      // WHEN
      const result = comp.trackId(1, new Feedback(123));

      // THEN
      expect(result).toEqual(123);
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Feedback(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);
      comp.reset();

      // THEN
      expect(comp.page).toEqual(0);
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.feedbacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['solved,asc', 'createdDate,desc', 'id']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.sortForm.patchValue({
        status: 'solved',
        creationDate: 'older',
      });

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['solved,desc', 'createdDate,asc', 'id']);
    });

    it('should update feedback', () => {
      comp.ngOnInit();

      const feedbackTest = new Feedback(12);

      comp.markFeedbackAsSolved(feedbackTest);

      expect(feedbackTest.solved).toBeTruthy();
    });
  });
});
