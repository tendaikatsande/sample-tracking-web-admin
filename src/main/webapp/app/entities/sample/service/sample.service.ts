import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISample, getSampleIdentifier } from '../sample.model';

export type EntityResponseType = HttpResponse<ISample>;
export type EntityArrayResponseType = HttpResponse<ISample[]>;

@Injectable({ providedIn: 'root' })
export class SampleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/samples');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sample: ISample): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sample);
    return this.http
      .post<ISample>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sample: ISample): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sample);
    return this.http
      .put<ISample>(`${this.resourceUrl}/${getSampleIdentifier(sample) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sample: ISample): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sample);
    return this.http
      .patch<ISample>(`${this.resourceUrl}/${getSampleIdentifier(sample) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISample>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISample[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSampleToCollectionIfMissing(sampleCollection: ISample[], ...samplesToCheck: (ISample | null | undefined)[]): ISample[] {
    const samples: ISample[] = samplesToCheck.filter(isPresent);
    if (samples.length > 0) {
      const sampleCollectionIdentifiers = sampleCollection.map(sampleItem => getSampleIdentifier(sampleItem)!);
      const samplesToAdd = samples.filter(sampleItem => {
        const sampleIdentifier = getSampleIdentifier(sampleItem);
        if (sampleIdentifier == null || sampleCollectionIdentifiers.includes(sampleIdentifier)) {
          return false;
        }
        sampleCollectionIdentifiers.push(sampleIdentifier);
        return true;
      });
      return [...samplesToAdd, ...sampleCollection];
    }
    return sampleCollection;
  }

  protected convertDateFromClient(sample: ISample): ISample {
    return Object.assign({}, sample, {
      dateCollected: sample.dateCollected?.isValid() ? sample.dateCollected.toJSON() : undefined,
      dateSynced: sample.dateSynced?.isValid() ? sample.dateSynced.toJSON() : undefined,
      createdDate: sample.createdDate?.isValid() ? sample.createdDate.toJSON() : undefined,
      lastModifiedDate: sample.lastModifiedDate?.isValid() ? sample.lastModifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCollected = res.body.dateCollected ? dayjs(res.body.dateCollected) : undefined;
      res.body.dateSynced = res.body.dateSynced ? dayjs(res.body.dateSynced) : undefined;
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sample: ISample) => {
        sample.dateCollected = sample.dateCollected ? dayjs(sample.dateCollected) : undefined;
        sample.dateSynced = sample.dateSynced ? dayjs(sample.dateSynced) : undefined;
        sample.createdDate = sample.createdDate ? dayjs(sample.createdDate) : undefined;
        sample.lastModifiedDate = sample.lastModifiedDate ? dayjs(sample.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
