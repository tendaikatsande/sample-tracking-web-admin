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
      id: 'AAAAAAA',
      name: 'AAAAAAA',
      prefix: 'AAAAAAA',
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

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TestType', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
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

      service.create(new TestType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TestType', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          prefix: 'BBBBBB',
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

    it('should partial update a TestType', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          createdBy: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new TestType()
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

    it('should return a list of TestType', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          prefix: 'BBBBBB',
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

    it('should delete a TestType', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTestTypeToCollectionIfMissing', () => {
      it('should add a TestType to an empty array', () => {
        const testType: ITestType = { id: 'ABC' };
        expectedResult = service.addTestTypeToCollectionIfMissing([], testType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(testType);
      });

      it('should not add a TestType to an array that contains it', () => {
        const testType: ITestType = { id: 'ABC' };
        const testTypeCollection: ITestType[] = [
          {
            ...testType,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addTestTypeToCollectionIfMissing(testTypeCollection, testType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TestType to an array that doesn't contain it", () => {
        const testType: ITestType = { id: 'ABC' };
        const testTypeCollection: ITestType[] = [{ id: 'CBA' }];
        expectedResult = service.addTestTypeToCollectionIfMissing(testTypeCollection, testType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(testType);
      });

      it('should add only unique TestType to an array', () => {
        const testTypeArray: ITestType[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'af89ebc5-3023-4e4f-bccb-bfe864252bee' }];
        const testTypeCollection: ITestType[] = [{ id: 'ABC' }];
        expectedResult = service.addTestTypeToCollectionIfMissing(testTypeCollection, ...testTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const testType: ITestType = { id: 'ABC' };
        const testType2: ITestType = { id: 'CBA' };
        expectedResult = service.addTestTypeToCollectionIfMissing([], testType, testType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(testType);
        expect(expectedResult).toContain(testType2);
      });

      it('should accept null and undefined values', () => {
        const testType: ITestType = { id: 'ABC' };
        expectedResult = service.addTestTypeToCollectionIfMissing([], null, testType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(testType);
      });

      it('should return initial array if no TestType is added', () => {
        const testTypeCollection: ITestType[] = [{ id: 'ABC' }];
        expectedResult = service.addTestTypeToCollectionIfMissing(testTypeCollection, undefined, null);
        expect(expectedResult).toEqual(testTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});