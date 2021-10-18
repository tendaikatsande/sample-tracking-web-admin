jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TestTypeService } from '../service/test-type.service';
import { ITestType, TestType } from '../test-type.model';

import { TestTypeUpdateComponent } from './test-type-update.component';

describe('Component Tests', () => {
  describe('TestType Management Update Component', () => {
    let comp: TestTypeUpdateComponent;
    let fixture: ComponentFixture<TestTypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let testTypeService: TestTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TestTypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TestTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestTypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      testTypeService = TestBed.inject(TestTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const testType: ITestType = { id: 456 };

        activatedRoute.data = of({ testType });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(testType));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TestType>>();
        const testType = { id: 123 };
        jest.spyOn(testTypeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ testType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: testType }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(testTypeService.update).toHaveBeenCalledWith(testType);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TestType>>();
        const testType = new TestType();
        jest.spyOn(testTypeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ testType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: testType }));
        saveSubject.complete();

        // THEN
        expect(testTypeService.create).toHaveBeenCalledWith(testType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TestType>>();
        const testType = { id: 123 };
        jest.spyOn(testTypeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ testType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(testTypeService.update).toHaveBeenCalledWith(testType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
