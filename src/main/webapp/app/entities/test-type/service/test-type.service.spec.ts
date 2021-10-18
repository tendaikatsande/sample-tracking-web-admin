import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITestType, TestType } from '../test-type.model';

import { TestTypeService } from './test-type.service';

describe('TestType Service', () => {
  let service: TestTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: ITestType;
  let expectedResult: ITestType | ITestType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TestTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      prefix: 'AAAAAAA',
      createdBy: 'AAAAAAA',
      modifiedBy: 'AAAAAAA',
      dateCreated: currentDate,
      dateModified: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TestType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCreated: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.create(new TestType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TestType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          prefix: 'BBBBBB',
          createdBy: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCreated: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TestType', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          createdBy: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
        },
        new TestType()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateCreated: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TestType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          prefix: 'BBBBBB',
          createdBy: 'BBBBBB',
          modifiedBy: 'BBBBBB',
          dateCreated: currentDate.format(DATE_TIME_FORMAT),
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCreated: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TestType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTestTypeToCollectionIfMissing', () => {
      it('should add a TestType to an empty array', () => {
        const testType: ITestType = { id: 123 };
        expectedResult = service.addTestTypeToCollectionIfMissing([], testType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(testType);
      });

      it('should not add a TestType to an array that contains it', () => {
        const testType: ITestType = { id: 123 };
        const testTypeCollection: ITestType[] = [
          {
            ...testType,
          },
          { id: 456 },
        ];
        expectedResult = service.addTestTypeToCollectionIfMissing(testTypeCollection, testType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TestType to an array that doesn't contain it", () => {
        const testType: ITestType = { id: 123 };
        const testTypeCollection: ITestType[] = [{ id: 456 }];
        expectedResult = service.addTestTypeToCollectionIfMissing(testTypeCollection, testType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(testType);
      });

      it('should add only unique TestType to an array', () => {
        const testTypeArray: ITestType[] = [{ id: 123 }, { id: 456 }, { id: 64029 }];
        const testTypeCollection: ITestType[] = [{ id: 123 }];
        expectedResult = service.addTestTypeToCollectionIfMissing(testTypeCollection, ...testTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const testType: ITestType = { id: 123 };
        const testType2: ITestType = { id: 456 };
        expectedResult = service.addTestTypeToCollectionIfMissing([], testType, testType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(testType);
        expect(expectedResult).toContain(testType2);
      });

      it('should accept null and undefined values', () => {
        const testType: ITestType = { id: 123 };
        expectedResult = service.addTestTypeToCollectionIfMissing([], null, testType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(testType);
      });

      it('should return initial array if no TestType is added', () => {
        const testTypeCollection: ITestType[] = [{ id: 123 }];
        expectedResult = service.addTestTypeToCollectionIfMissing(testTypeCollection, undefined, null);
        expect(expectedResult).toEqual(testTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
