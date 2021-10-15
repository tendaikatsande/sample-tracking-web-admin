import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LaboratoryDetailComponent } from './laboratory-detail.component';

describe('Component Tests', () => {
  describe('Laboratory Management Detail Component', () => {
    let comp: LaboratoryDetailComponent;
    let fixture: ComponentFixture<LaboratoryDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [LaboratoryDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ laboratory: { id: 'ABC' } }) },
          },
        ],
      })
        .overrideTemplate(LaboratoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LaboratoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load laboratory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.laboratory).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
  });
});
