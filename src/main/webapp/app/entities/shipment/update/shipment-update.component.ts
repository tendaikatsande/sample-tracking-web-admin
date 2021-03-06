import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IShipment, Shipment } from '../shipment.model';
import { ShipmentService } from '../service/shipment.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-shipment-update',
  templateUrl: './shipment-update.component.html',
})
export class ShipmentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    appId: [],
    description: [],
    clientId: [],
    samples: [],
    status: [],
    dateCreated: [],
    dateModified: [],
    riderId: [],
    riderName: [],
    destination: [],
    clusterClientId: [],
    temperatureOrigin: [],
    temperatureDestination: [],
    isModifiedByHub: [],
    isModifiedByFacility: [],
    isModifiedByLaboratory: [],
    isModifiedByCourier: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected shipmentService: ShipmentService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipment }) => {
      if (shipment.id === undefined) {
        const today = dayjs().startOf('day');
        shipment.createdDate = today;
        shipment.lastModifiedDate = today;
      }

      this.updateForm(shipment);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('sampleTrackingApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipment = this.createFromForm();
    if (shipment.id !== undefined) {
      this.subscribeToSaveResponse(this.shipmentService.update(shipment));
    } else {
      this.subscribeToSaveResponse(this.shipmentService.create(shipment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipment>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(shipment: IShipment): void {
    this.editForm.patchValue({
      id: shipment.id,
      appId: shipment.appId,
      description: shipment.description,
      clientId: shipment.clientId,
      samples: shipment.samples,
      status: shipment.status,
      dateCreated: shipment.dateCreated,
      dateModified: shipment.dateModified,
      riderId: shipment.riderId,
      riderName: shipment.riderName,
      destination: shipment.destination,
      clusterClientId: shipment.clusterClientId,
      temperatureOrigin: shipment.temperatureOrigin,
      temperatureDestination: shipment.temperatureDestination,
      isModifiedByHub: shipment.isModifiedByHub,
      isModifiedByFacility: shipment.isModifiedByFacility,
      isModifiedByLaboratory: shipment.isModifiedByLaboratory,
      isModifiedByCourier: shipment.isModifiedByCourier,
      createdBy: shipment.createdBy,
      createdDate: shipment.createdDate ? shipment.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: shipment.lastModifiedBy,
      lastModifiedDate: shipment.lastModifiedDate ? shipment.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IShipment {
    return {
      ...new Shipment(),
      id: this.editForm.get(['id'])!.value,
      appId: this.editForm.get(['appId'])!.value,
      description: this.editForm.get(['description'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
      samples: this.editForm.get(['samples'])!.value,
      status: this.editForm.get(['status'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateModified: this.editForm.get(['dateModified'])!.value,
      riderId: this.editForm.get(['riderId'])!.value,
      riderName: this.editForm.get(['riderName'])!.value,
      destination: this.editForm.get(['destination'])!.value,
      clusterClientId: this.editForm.get(['clusterClientId'])!.value,
      temperatureOrigin: this.editForm.get(['temperatureOrigin'])!.value,
      temperatureDestination: this.editForm.get(['temperatureDestination'])!.value,
      isModifiedByHub: this.editForm.get(['isModifiedByHub'])!.value,
      isModifiedByFacility: this.editForm.get(['isModifiedByFacility'])!.value,
      isModifiedByLaboratory: this.editForm.get(['isModifiedByLaboratory'])!.value,
      isModifiedByCourier: this.editForm.get(['isModifiedByCourier'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
