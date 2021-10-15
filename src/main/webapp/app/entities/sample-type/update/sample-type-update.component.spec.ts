jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SampleTypeService } from '../service/sample-type.service';
import { ISampleType, SampleType } from '../sample-type.model';

import { SampleTypeUpdateComponent } from './sample-type-update.component';

describe('Component Tests', () => {
  describe('SampleType Management Update Component', () => {
    let comp: SampleTypeUpdateComponent;
    let fixture: ComponentFixture<SampleTypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let sampleTypeService: SampleTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SampleTypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SampleTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SampleTypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      sampleTypeService = TestBed.inject(SampleTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const sampleType: ISampleType = { id: 'CBA' };

        activatedRoute.data = of({ sampleType });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(sampleType));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SampleType>>();
        const sampleType = { id: 'ABC' };
        jest.spyOn(sampleTypeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ sampleType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sampleType }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(sampleTypeService.update).toHaveBeenCalledWith(sampleType);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SampleType>>();
        const sampleType = new SampleType();
        jest.spyOn(sampleTypeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ sampleType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sampleType }));
        saveSubject.complete();

        // THEN
        expect(sampleTypeService.create).toHaveBeenCalledWith(sampleType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SampleType>>();
        const sampleType = { id: 'ABC' };
        jest.spyOn(sampleTypeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ sampleType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(sampleTypeService.update).toHaveBeenCalledWith(sampleType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
