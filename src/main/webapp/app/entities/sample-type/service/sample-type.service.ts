import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISampleType, getSampleTypeIdentifier } from '../sample-type.model';

export type EntityResponseType = HttpResponse<ISampleType>;
export type EntityArrayResponseType = HttpResponse<ISampleType[]>;

@Injectable({ providedIn: 'root' })
export class SampleTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sample-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sampleType: ISampleType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sampleType);
    return this.http
      .post<ISampleType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sampleType: ISampleType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sampleType);
    return this.http
      .put<ISampleType>(`${this.resourceUrl}/${getSampleTypeIdentifier(sampleType) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(sampleType: ISampleType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sampleType);
    return this.http
      .patch<ISampleType>(`${this.resourceUrl}/${getSampleTypeIdentifier(sampleType) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ISampleType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISampleType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSampleTypeToCollectionIfMissing(
    sampleTypeCollection: ISampleType[],
    ...sampleTypesToCheck: (ISampleType | null | undefined)[]
  ): ISampleType[] {
    const sampleTypes: ISampleType[] = sampleTypesToCheck.filter(isPresent);
    if (sampleTypes.length > 0) {
      const sampleTypeCollectionIdentifiers = sampleTypeCollection.map(sampleTypeItem => getSampleTypeIdentifier(sampleTypeItem)!);
      const sampleTypesToAdd = sampleTypes.filter(sampleTypeItem => {
        const sampleTypeIdentifier = getSampleTypeIdentifier(sampleTypeItem);
        if (sampleTypeIdentifier == null || sampleTypeCollectionIdentifiers.includes(sampleTypeIdentifier)) {
          return false;
        }
        sampleTypeCollectionIdentifiers.push(sampleTypeIdentifier);
        return true;
      });
      return [...sampleTypesToAdd, ...sampleTypeCollection];
    }
    return sampleTypeCollection;
  }

  protected convertDateFromClient(sampleType: ISampleType): ISampleType {
    return Object.assign({}, sampleType, {
      createdDate: sampleType.createdDate?.isValid() ? sampleType.createdDate.toJSON() : undefined,
      lastModifiedDate: sampleType.lastModifiedDate?.isValid() ? sampleType.lastModifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sampleType: ISampleType) => {
        sampleType.createdDate = sampleType.createdDate ? dayjs(sampleType.createdDate) : undefined;
        sampleType.lastModifiedDate = sampleType.lastModifiedDate ? dayjs(sampleType.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
