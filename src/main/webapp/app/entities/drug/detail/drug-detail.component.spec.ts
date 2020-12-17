import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Drug } from '../drug.model';

import { DrugDetailComponent } from './drug-detail.component';

describe('Component Tests', () => {
  describe('Drug Management Detail Component', () => {
    let comp: DrugDetailComponent;
    let fixture: ComponentFixture<DrugDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DrugDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ drug: new Drug(123) }) },
          },
        ],
      })
        .overrideTemplate(DrugDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrugDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load drug on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.drug).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
