jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LaboratoryService } from '../service/laboratory.service';
import { ILaboratory, Laboratory } from '../laboratory.model';

import { LaboratoryUpdateComponent } from './laboratory-update.component';

describe('Component Tests', () => {
  describe('Laboratory Management Update Component', () => {
    let comp: LaboratoryUpdateComponent;
    let fixture: ComponentFixture<LaboratoryUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let laboratoryService: LaboratoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LaboratoryUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(LaboratoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LaboratoryUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      laboratoryService = TestBed.inject(LaboratoryService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const laboratory: ILaboratory = { id: 'CBA' };

        activatedRoute.data = of({ laboratory });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(laboratory));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Laboratory>>();
        const laboratory = { id: 'ABC' };
        jest.spyOn(laboratoryService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ laboratory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: laboratory }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(laboratoryService.update).toHaveBeenCalledWith(laboratory);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Laboratory>>();
        const laboratory = new Laboratory();
        jest.spyOn(laboratoryService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ laboratory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: laboratory }));
        saveSubject.complete();

        // THEN
        expect(laboratoryService.create).toHaveBeenCalledWith(laboratory);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Laboratory>>();
        const laboratory = { id: 'ABC' };
        jest.spyOn(laboratoryService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ laboratory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(laboratoryService.update).toHaveBeenCalledWith(laboratory);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
