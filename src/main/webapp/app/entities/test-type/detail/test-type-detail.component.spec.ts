import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestTypeDetailComponent } from './test-type-detail.component';

describe('Component Tests', () => {
  describe('TestType Management Detail Component', () => {
    let comp: TestTypeDetailComponent;
    let fixture: ComponentFixture<TestTypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TestTypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ testType: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TestTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TestTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load testType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.testType).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
