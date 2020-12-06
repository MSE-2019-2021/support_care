import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Administration } from 'app/shared/model/administration.model';

import { AdministrationDetailComponent } from './administration-detail.component';

describe('Component Tests', () => {
  describe('Administration Management Detail Component', () => {
    let comp: AdministrationDetailComponent;
    let fixture: ComponentFixture<AdministrationDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AdministrationDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ administration: new Administration(123) }) },
          },
        ],
      })
        .overrideTemplate(AdministrationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdministrationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load administration on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.administration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
