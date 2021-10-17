import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISample, Sample } from '../sample.model';
import { SampleService } from '../service/sample.service';

@Component({
  selector: 'jhi-sample-update',
  templateUrl: './sample-update.component.html',
})
export class SampleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    appId: [],
    clientSampleId: [],
    clientPatientId: [],
    labId: [],
    clientId: [],
    sampleType: [],
    testId: [],
    dateCollected: [],
    collectedBy: [],
    status: [],
    comment: [],
    synced: [],
    dateSynced: [],
    labReferenceId: [],
    location: [],
    result: [],
    resultReceivedBy: [],
    shipmentId: [],
    clientContact: [],
    temperatureAtHub: [],
    temperatureAtLab: [],
    isModifiedByHub: [],
    isModifiedByFacility: [],
    isModifiedByLaboratory: [],
    isModifiedByCourrier: [],
    createdBy: [],
    modifiedBy: [],
    dateCreated: [],
    dateModified: [],
  });

  constructor(protected sampleService: SampleService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sample }) => {
      if (sample.id === undefined) {
        const today = dayjs().startOf('day');
        sample.dateCollected = today;
        sample.dateSynced = today;
        sample.modifiedBy = today;
        sample.dateModified = today;
      }

      this.updateForm(sample);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sample = this.createFromForm();
    if (sample.id !== undefined) {
      this.subscribeToSaveResponse(this.sampleService.update(sample));
    } else {
      this.subscribeToSaveResponse(this.sampleService.create(sample));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISample>>): void {
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

  protected updateForm(sample: ISample): void {
    this.editForm.patchValue({
      id: sample.id,
      appId: sample.appId,
      clientSampleId: sample.clientSampleId,
      clientPatientId: sample.clientPatientId,
      labId: sample.labId,
      clientId: sample.clientId,
      sampleType: sample.sampleType,
      testId: sample.testId,
      dateCollected: sample.dateCollected ? sample.dateCollected.format(DATE_TIME_FORMAT) : null,
      collectedBy: sample.collectedBy,
      status: sample.status,
      comment: sample.comment,
      synced: sample.synced,
      dateSynced: sample.dateSynced ? sample.dateSynced.format(DATE_TIME_FORMAT) : null,
      labReferenceId: sample.labReferenceId,
      location: sample.location,
      result: sample.result,
      resultReceivedBy: sample.resultReceivedBy,
      shipmentId: sample.shipmentId,
      clientContact: sample.clientContact,
      temperatureAtHub: sample.temperatureAtHub,
      temperatureAtLab: sample.temperatureAtLab,
      isModifiedByHub: sample.isModifiedByHub,
      isModifiedByFacility: sample.isModifiedByFacility,
      isModifiedByLaboratory: sample.isModifiedByLaboratory,
      isModifiedByCourrier: sample.isModifiedByCourrier,
      createdBy: sample.createdBy,
      modifiedBy: sample.modifiedBy ? sample.modifiedBy.format(DATE_TIME_FORMAT) : null,
      dateCreated: sample.dateCreated,
      dateModified: sample.dateModified ? sample.dateModified.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISample {
    return {
      ...new Sample(),
      id: this.editForm.get(['id'])!.value,
      appId: this.editForm.get(['appId'])!.value,
      clientSampleId: this.editForm.get(['clientSampleId'])!.value,
      clientPatientId: this.editForm.get(['clientPatientId'])!.value,
      labId: this.editForm.get(['labId'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
      sampleType: this.editForm.get(['sampleType'])!.value,
      testId: this.editForm.get(['testId'])!.value,
      dateCollected: this.editForm.get(['dateCollected'])!.value
        ? dayjs(this.editForm.get(['dateCollected'])!.value, DATE_TIME_FORMAT)
        : undefined,
      collectedBy: this.editForm.get(['collectedBy'])!.value,
      status: this.editForm.get(['status'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      synced: this.editForm.get(['synced'])!.value,
      dateSynced: this.editForm.get(['dateSynced'])!.value ? dayjs(this.editForm.get(['dateSynced'])!.value, DATE_TIME_FORMAT) : undefined,
      labReferenceId: this.editForm.get(['labReferenceId'])!.value,
      location: this.editForm.get(['location'])!.value,
      result: this.editForm.get(['result'])!.value,
      resultReceivedBy: this.editForm.get(['resultReceivedBy'])!.value,
      shipmentId: this.editForm.get(['shipmentId'])!.value,
      clientContact: this.editForm.get(['clientContact'])!.value,
      temperatureAtHub: this.editForm.get(['temperatureAtHub'])!.value,
      temperatureAtLab: this.editForm.get(['temperatureAtLab'])!.value,
      isModifiedByHub: this.editForm.get(['isModifiedByHub'])!.value,
      isModifiedByFacility: this.editForm.get(['isModifiedByFacility'])!.value,
      isModifiedByLaboratory: this.editForm.get(['isModifiedByLaboratory'])!.value,
      isModifiedByCourrier: this.editForm.get(['isModifiedByCourrier'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value ? dayjs(this.editForm.get(['modifiedBy'])!.value, DATE_TIME_FORMAT) : undefined,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateModified: this.editForm.get(['dateModified'])!.value
        ? dayjs(this.editForm.get(['dateModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
