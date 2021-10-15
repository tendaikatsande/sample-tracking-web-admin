import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPatient, Patient } from '../patient.model';
import { PatientService } from '../service/patient.service';

@Component({
  selector: 'jhi-patient-update',
  templateUrl: './patient-update.component.html',
})
export class PatientUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    appId: [],
    firstName: [],
    lastName: [],
    gender: [],
    dob: [],
    client: [],
    clientPatientId: [],
    cohortNumber: [],
    dateCreated: [],
    dateModified: [],
    phoneNumber: [],
    createdBy: [],
    modifiedBy: [],
  });

  constructor(protected patientService: PatientService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patient }) => {
      if (patient.id === undefined) {
        const today = dayjs().startOf('day');
        patient.dateModified = today;
        patient.modifiedBy = today;
      }

      this.updateForm(patient);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patient = this.createFromForm();
    if (patient.id !== undefined) {
      this.subscribeToSaveResponse(this.patientService.update(patient));
    } else {
      this.subscribeToSaveResponse(this.patientService.create(patient));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>): void {
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

  protected updateForm(patient: IPatient): void {
    this.editForm.patchValue({
      id: patient.id,
      appId: patient.appId,
      firstName: patient.firstName,
      lastName: patient.lastName,
      gender: patient.gender,
      dob: patient.dob,
      client: patient.client,
      clientPatientId: patient.clientPatientId,
      cohortNumber: patient.cohortNumber,
      dateCreated: patient.dateCreated,
      dateModified: patient.dateModified ? patient.dateModified.format(DATE_TIME_FORMAT) : null,
      phoneNumber: patient.phoneNumber,
      createdBy: patient.createdBy,
      modifiedBy: patient.modifiedBy ? patient.modifiedBy.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IPatient {
    return {
      ...new Patient(),
      id: this.editForm.get(['id'])!.value,
      appId: this.editForm.get(['appId'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      client: this.editForm.get(['client'])!.value,
      clientPatientId: this.editForm.get(['clientPatientId'])!.value,
      cohortNumber: this.editForm.get(['cohortNumber'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateModified: this.editForm.get(['dateModified'])!.value
        ? dayjs(this.editForm.get(['dateModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value ? dayjs(this.editForm.get(['modifiedBy'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
