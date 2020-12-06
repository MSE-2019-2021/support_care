import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Content } from 'app/shared/model/content.model';
import { DataUtils } from 'app/core/util/data-util.service';

import { ContentDetailComponent } from './content-detail.component';

describe('Component Tests', () => {
  describe('Content Management Detail Component', () => {
    let comp: ContentDetailComponent;
    let fixture: ComponentFixture<ContentDetailComponent>;
    let dataUtils: DataUtils;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ContentDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ content: new Content(123) }) },
          },
        ],
      })
        .overrideTemplate(ContentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = TestBed.inject(DataUtils);
    });

    describe('OnInit', () => {
      it('Should load content on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.content).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from DataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from DataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeBase64, fakeContentType);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeBase64, fakeContentType);
      });
    });
  });
});
