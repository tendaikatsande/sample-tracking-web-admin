import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPatient, getPatientIdentifier } from '../patient.model';

export type EntityResponseType = HttpResponse<IPatient>;
export type EntityArrayResponseType = HttpResponse<IPatient[]>;

@Injectable({ providedIn: 'root' })
export class PatientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/patients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(patient: IPatient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patient);
    return this.http
      .post<IPatient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patient: IPatient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patient);
    return this.http
      .put<IPatient>(`${this.resourceUrl}/${getPatientIdentifier(patient) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(patient: IPatient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patient);
    return this.http
      .patch<IPatient>(`${this.resourceUrl}/${getPatientIdentifier(patient) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IPatient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatient[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPatientToCollectionIfMissing(patientCollection: IPatient[], ...patientsToCheck: (IPatient | null | undefined)[]): IPatient[] {
    const patients: IPatient[] = patientsToCheck.filter(isPresent);
    if (patients.length > 0) {
      const patientCollectionIdentifiers = patientCollection.map(patientItem => getPatientIdentifier(patientItem)!);
      const patientsToAdd = patients.filter(patientItem => {
        const patientIdentifier = getPatientIdentifier(patientItem);
        if (patientIdentifier == null || patientCollectionIdentifiers.includes(patientIdentifier)) {
          return false;
        }
        patientCollectionIdentifiers.push(patientIdentifier);
        return true;
      });
      return [...patientsToAdd, ...patientCollection];
    }
    return patientCollection;
  }

  protected convertDateFromClient(patient: IPatient): IPatient {
    return Object.assign({}, patient, {
      dateModified: patient.dateModified?.isValid() ? patient.dateModified.toJSON() : undefined,
      modifiedBy: patient.modifiedBy?.isValid() ? patient.modifiedBy.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateModified = res.body.dateModified ? dayjs(res.body.dateModified) : undefined;
      res.body.modifiedBy = res.body.modifiedBy ? dayjs(res.body.modifiedBy) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((patient: IPatient) => {
        patient.dateModified = patient.dateModified ? dayjs(patient.dateModified) : undefined;
        patient.modifiedBy = patient.modifiedBy ? dayjs(patient.modifiedBy) : undefined;
      });
    }
    return res;
  }
}