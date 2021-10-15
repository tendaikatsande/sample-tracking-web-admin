jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ShipmentService } from '../service/shipment.service';
import { IShipment, Shipment } from '../shipment.model';

import { ShipmentUpdateComponent } from './shipment-update.component';

describe('Component Tests', () => {
  describe('Shipment Management Update Component', () => {
    let comp: ShipmentUpdateComponent;
    let fixture: ComponentFixture<ShipmentUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let shipmentService: ShipmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ShipmentUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ShipmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShipmentUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      shipmentService = TestBed.inject(ShipmentService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const shipment: IShipment = { id: 'CBA' };

        activatedRoute.data = of({ shipment });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(shipment));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Shipment>>();
        const shipment = { id: 'ABC' };
        jest.spyOn(shipmentService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ shipment });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: shipment }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(shipmentService.update).toHaveBeenCalledWith(shipment);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Shipment>>();
        const shipment = new Shipment();
        jest.spyOn(shipmentService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ shipment });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: shipment }));
        saveSubject.complete();

        // THEN
        expect(shipmentService.create).toHaveBeenCalledWith(shipment);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Shipment>>();
        const shipment = { id: 'ABC' };
        jest.spyOn(shipmentService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ shipment });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(shipmentService.update).toHaveBeenCalledWith(shipment);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
