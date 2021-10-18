import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISampleType, SampleType } from '../sample-type.model';

import { SampleTypeService } from './sample-type.service';

describe('SampleType Service', () => {
  let service: SampleTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: ISampleType;
  let expectedResult: ISampleType | ISampleType[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SampleTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
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

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SampleType', () => {
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

      service.create(new SampleType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SampleType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should partial update a SampleType', () => {
      const patchObject = Object.assign(
        {
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new SampleType()
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

    it('should return a list of SampleType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a SampleType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSampleTypeToCollectionIfMissing', () => {
      it('should add a SampleType to an empty array', () => {
        const sampleType: ISampleType = { id: 123 };
        expectedResult = service.addSampleTypeToCollectionIfMissing([], sampleType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sampleType);
      });

      it('should not add a SampleType to an array that contains it', () => {
        const sampleType: ISampleType = { id: 123 };
        const sampleTypeCollection: ISampleType[] = [
          {
            ...sampleType,
          },
          { id: 456 },
        ];
        expectedResult = service.addSampleTypeToCollectionIfMissing(sampleTypeCollection, sampleType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SampleType to an array that doesn't contain it", () => {
        const sampleType: ISampleType = { id: 123 };
        const sampleTypeCollection: ISampleType[] = [{ id: 456 }];
        expectedResult = service.addSampleTypeToCollectionIfMissing(sampleTypeCollection, sampleType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sampleType);
      });

      it('should add only unique SampleType to an array', () => {
        const sampleTypeArray: ISampleType[] = [{ id: 123 }, { id: 456 }, { id: 19777 }];
        const sampleTypeCollection: ISampleType[] = [{ id: 123 }];
        expectedResult = service.addSampleTypeToCollectionIfMissing(sampleTypeCollection, ...sampleTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sampleType: ISampleType = { id: 123 };
        const sampleType2: ISampleType = { id: 456 };
        expectedResult = service.addSampleTypeToCollectionIfMissing([], sampleType, sampleType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sampleType);
        expect(expectedResult).toContain(sampleType2);
      });

      it('should accept null and undefined values', () => {
        const sampleType: ISampleType = { id: 123 };
        expectedResult = service.addSampleTypeToCollectionIfMissing([], null, sampleType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sampleType);
      });

      it('should return initial array if no SampleType is added', () => {
        const sampleTypeCollection: ISampleType[] = [{ id: 123 }];
        expectedResult = service.addSampleTypeToCollectionIfMissing(sampleTypeCollection, undefined, null);
        expect(expectedResult).toEqual(sampleTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
