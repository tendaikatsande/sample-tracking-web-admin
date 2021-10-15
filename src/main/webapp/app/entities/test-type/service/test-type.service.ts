import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITestType, getTestTypeIdentifier } from '../test-type.model';

export type EntityResponseType = HttpResponse<ITestType>;
export type EntityArrayResponseType = HttpResponse<ITestType[]>;

@Injectable({ providedIn: 'root' })
export class TestTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/test-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(testType: ITestType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testType);
    return this.http
      .post<ITestType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(testType: ITestType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testType);
    return this.http
      .put<ITestType>(`${this.resourceUrl}/${getTestTypeIdentifier(testType) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(testType: ITestType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testType);
    return this.http
      .patch<ITestType>(`${this.resourceUrl}/${getTestTypeIdentifier(testType) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITestType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITestType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTestTypeToCollectionIfMissing(testTypeCollection: ITestType[], ...testTypesToCheck: (ITestType | null | undefined)[]): ITestType[] {
    const testTypes: ITestType[] = testTypesToCheck.filter(isPresent);
    if (testTypes.length > 0) {
      const testTypeCollectionIdentifiers = testTypeCollection.map(testTypeItem => getTestTypeIdentifier(testTypeItem)!);
      const testTypesToAdd = testTypes.filter(testTypeItem => {
        const testTypeIdentifier = getTestTypeIdentifier(testTypeItem);
        if (testTypeIdentifier == null || testTypeCollectionIdentifiers.includes(testTypeIdentifier)) {
          return false;
        }
        testTypeCollectionIdentifiers.push(testTypeIdentifier);
        return true;
      });
      return [...testTypesToAdd, ...testTypeCollection];
    }
    return testTypeCollection;
  }

  protected convertDateFromClient(testType: ITestType): ITestType {
    return Object.assign({}, testType, {
      createdDate: testType.createdDate?.isValid() ? testType.createdDate.toJSON() : undefined,
      lastModifiedDate: testType.lastModifiedDate?.isValid() ? testType.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((testType: ITestType) => {
        testType.createdDate = testType.createdDate ? dayjs(testType.createdDate) : undefined;
        testType.lastModifiedDate = testType.lastModifiedDate ? dayjs(testType.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
