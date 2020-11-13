import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DrugDetailComponent } from 'app/entities/drug/drug-detail.component';
import { Drug } from 'app/shared/model/drug.model';

describe('Component Tests', () => {
  describe('Drug Management Detail Component', () => {
    let comp: DrugDetailComponent;
    let fixture: ComponentFixture<DrugDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DrugDetailComponent],
        providers: [
          {
            imports: [HttpClientTestingModule],
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
