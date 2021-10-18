import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILaboratory, Laboratory } from '../laboratory.model';

import { LaboratoryService } from './laboratory.service';

describe('Laboratory Service', () => {
  let service: LaboratoryService;
  let httpMock: HttpTestingController;
  let elemDefault: ILaboratory;
  let expectedResult: ILaboratory | ILaboratory[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LaboratoryService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      type: 'AAAAAAA',
      code: 'AAAAAAA',
      createdBy: 'AAAAAAA',
      createdDate: currentDate,
      lastModifiedBy: 'AAAAAAA',
      lastModifiedDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Laboratory', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Laboratory()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Laboratory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          type: 'BBBBBB',
          code: 'BBBBBB',
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Laboratory', () => {
      const patchObject = Object.assign(
        {
          type: 'BBBBBB',
          code: 'BBBBBB',
          createdBy: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Laboratory()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Laboratory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          type: 'BBBBBB',
          code: 'BBBBBB',
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdDate: currentDate,
          lastModifiedDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Laboratory', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLaboratoryToCollectionIfMissing', () => {
      it('should add a Laboratory to an empty array', () => {
        const laboratory: ILaboratory = { id: 123 };
        expectedResult = service.addLaboratoryToCollectionIfMissing([], laboratory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(laboratory);
      });

      it('should not add a Laboratory to an array that contains it', () => {
        const laboratory: ILaboratory = { id: 123 };
        const laboratoryCollection: ILaboratory[] = [
          {
            ...laboratory,
          },
          { id: 456 },
        ];
        expectedResult = service.addLaboratoryToCollectionIfMissing(laboratoryCollection, laboratory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Laboratory to an array that doesn't contain it", () => {
        const laboratory: ILaboratory = { id: 123 };
        const laboratoryCollection: ILaboratory[] = [{ id: 456 }];
        expectedResult = service.addLaboratoryToCollectionIfMissing(laboratoryCollection, laboratory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(laboratory);
      });

      it('should add only unique Laboratory to an array', () => {
        const laboratoryArray: ILaboratory[] = [{ id: 123 }, { id: 456 }, { id: 2482 }];
        const laboratoryCollection: ILaboratory[] = [{ id: 123 }];
        expectedResult = service.addLaboratoryToCollectionIfMissing(laboratoryCollection, ...laboratoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const laboratory: ILaboratory = { id: 123 };
        const laboratory2: ILaboratory = { id: 456 };
        expectedResult = service.addLaboratoryToCollectionIfMissing([], laboratory, laboratory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(laboratory);
        expect(expectedResult).toContain(laboratory2);
      });

      it('should accept null and undefined values', () => {
        const laboratory: ILaboratory = { id: 123 };
        expectedResult = service.addLaboratoryToCollectionIfMissing([], null, laboratory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(laboratory);
      });

      it('should return initial array if no Laboratory is added', () => {
        const laboratoryCollection: ILaboratory[] = [{ id: 123 }];
        expectedResult = service.addLaboratoryToCollectionIfMissing(laboratoryCollection, undefined, null);
        expect(expectedResult).toEqual(laboratoryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
