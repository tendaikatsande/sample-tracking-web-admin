import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILaboratory, getLaboratoryIdentifier } from '../laboratory.model';

export type EntityResponseType = HttpResponse<ILaboratory>;
export type EntityArrayResponseType = HttpResponse<ILaboratory[]>;

@Injectable({ providedIn: 'root' })
export class LaboratoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/laboratories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(laboratory: ILaboratory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(laboratory);
    return this.http
      .post<ILaboratory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(laboratory: ILaboratory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(laboratory);
    return this.http
      .put<ILaboratory>(`${this.resourceUrl}/${getLaboratoryIdentifier(laboratory) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(laboratory: ILaboratory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(laboratory);
    return this.http
      .patch<ILaboratory>(`${this.resourceUrl}/${getLaboratoryIdentifier(laboratory) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ILaboratory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILaboratory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLaboratoryToCollectionIfMissing(
    laboratoryCollection: ILaboratory[],
    ...laboratoriesToCheck: (ILaboratory | null | undefined)[]
  ): ILaboratory[] {
    const laboratories: ILaboratory[] = laboratoriesToCheck.filter(isPresent);
    if (laboratories.length > 0) {
      const laboratoryCollectionIdentifiers = laboratoryCollection.map(laboratoryItem => getLaboratoryIdentifier(laboratoryItem)!);
      const laboratoriesToAdd = laboratories.filter(laboratoryItem => {
        const laboratoryIdentifier = getLaboratoryIdentifier(laboratoryItem);
        if (laboratoryIdentifier == null || laboratoryCollectionIdentifiers.includes(laboratoryIdentifier)) {
          return false;
        }
        laboratoryCollectionIdentifiers.push(laboratoryIdentifier);
        return true;
      });
      return [...laboratoriesToAdd, ...laboratoryCollection];
    }
    return laboratoryCollection;
  }

  protected convertDateFromClient(laboratory: ILaboratory): ILaboratory {
    return Object.assign({}, laboratory, {
      createdDate: laboratory.createdDate?.isValid() ? laboratory.createdDate.toJSON() : undefined,
      lastModifiedDate: laboratory.lastModifiedDate?.isValid() ? laboratory.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((laboratory: ILaboratory) => {
        laboratory.createdDate = laboratory.createdDate ? dayjs(laboratory.createdDate) : undefined;
        laboratory.lastModifiedDate = laboratory.lastModifiedDate ? dayjs(laboratory.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
