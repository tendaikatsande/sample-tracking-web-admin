import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISample, Sample } from '../sample.model';

import { SampleService } from './sample.service';

describe('Sample Service', () => {
  let service: SampleService;
  let httpMock: HttpTestingController;
  let elemDefault: ISample;
  let expectedResult: ISample | ISample[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SampleService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      appId: 'AAAAAAA',
      clientSampleId: 'AAAAAAA',
      clientPatientId: 'AAAAAAA',
      labId: 'AAAAAAA',
      clientId: 'AAAAAAA',
      sampleType: 'AAAAAAA',
      testId: 'AAAAAAA',
      dateCollected: currentDate,
      collectedBy: 'AAAAAAA',
      status: 'AAAAAAA',
      comment: 'AAAAAAA',
      synced: false,
      dateSynced: currentDate,
      labReferenceId: 'AAAAAAA',
      location: 'AAAAAAA',
      result: 'AAAAAAA',
      resultReceivedBy: 'AAAAAAA',
      shipmentId: 'AAAAAAA',
      clientContact: 'AAAAAAA',
      temperatureAtHub: 'AAAAAAA',
      temperatureAtLab: 'AAAAAAA',
      isModifiedByHub: false,
      isModifiedByFacility: false,
      isModifiedByLaboratory: false,
      isModifiedByCourrier: false,
      createdBy: 'AAAAAAA',
      modifiedBy: currentDate,
      dateCreated: 'AAAAAAA',
      dateModified: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateCollected: currentDate.format(DATE_TIME_FORMAT),
          dateSynced: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: currentDate.format(DATE_TIME_FORMAT),
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Sample', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          dateCollected: currentDate.format(DATE_TIME_FORMAT),
          dateSynced: currentDate.format(DATE_TIME_FORMAT),
          modifiedBy: currentDate.format(DATE_TIME_FORMAT),
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCollected: currentDate,
          dateSynced: currentDate,
          modifiedBy: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.create(new Sample()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Sample', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          appId: 'BBBBBB',
          clientSampleId: 'BBBBBB',
          clientPatientId: 'BBBBBB',
          labId: 'BBBBBB',
          clientId: 'BBBBBB',
          sampleType: 'BBBBBB',
          testId: 'BBBBBB',
          dateCollected: currentDate.format(DATE_TIME_FORMAT),
          collectedBy: 'BBBBBB',
          status: 'BBBBBB',
          comment: 'BBBBBB',
          synced: true,
          dateSynced: currentDate.format(DATE_TIME_FORMAT),
          labReferenceId: 'BBBBBB',
          location: 'BBBBBB',
          result: 'BBBBBB',
          resultReceivedBy: 'BBBBBB',
          shipmentId: 'BBBBBB',
          clientContact: 'BBBBBB',
          temperatureAtHub: 'BBBBBB',
          temperatureAtLab: 'BBBBBB',
          isModifiedByHub: true,
          isModifiedByFacility: true,
          isModifiedByLaboratory: true,
          isModifiedByCourrier: true,
          createdBy: 'BBBBBB',
          modifiedBy: currentDate.format(DATE_TIME_FORMAT),
          dateCreated: 'BBBBBB',
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCollected: currentDate,
          dateSynced: currentDate,
          modifiedBy: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Sample', () => {
      const patchObject = Object.assign(
        {
          clientSampleId: 'BBBBBB',
          testId: 'BBBBBB',
          dateCollected: currentDate.format(DATE_TIME_FORMAT),
          collectedBy: 'BBBBBB',
          status: 'BBBBBB',
          comment: 'BBBBBB',
          synced: true,
          dateSynced: currentDate.format(DATE_TIME_FORMAT),
          resultReceivedBy: 'BBBBBB',
          clientContact: 'BBBBBB',
          temperatureAtLab: 'BBBBBB',
          isModifiedByHub: true,
          isModifiedByFacility: true,
          isModifiedByLaboratory: true,
          createdBy: 'BBBBBB',
          dateCreated: 'BBBBBB',
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        new Sample()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateCollected: currentDate,
          dateSynced: currentDate,
          modifiedBy: currentDate,
          dateModified: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Sample', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          appId: 'BBBBBB',
          clientSampleId: 'BBBBBB',
          clientPatientId: 'BBBBBB',
          labId: 'BBBBBB',
          clientId: 'BBBBBB',
          sampleType: 'BBBBBB',
          testId: 'BBBBBB',
          dateCollected: currentDate.format(DATE_TIME_FORMAT),
          collectedBy: 'BBBBBB',
          status: 'BBBBBB',
          comment: 'BBBBBB',
          synced: true,
          dateSynced: currentDate.format(DATE_TIME_FORMAT),
          labReferenceId: 'BBBBBB',
          location: 'BBBBBB',
          result: 'BBBBBB',
          resultReceivedBy: 'BBBBBB',
          shipmentId: 'BBBBBB',
          clientContact: 'BBBBBB',
          temperatureAtHub: 'BBBBBB',
          temperatureAtLab: 'BBBBBB',
          isModifiedByHub: true,
          isModifiedByFacility: true,
          isModifiedByLaboratory: true,
          isModifiedByCourrier: true,
          createdBy: 'BBBBBB',
          modifiedBy: currentDate.format(DATE_TIME_FORMAT),
          dateCreated: 'BBBBBB',
          dateModified: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateCollected: currentDate,
          dateSynced: currentDate,
          modifiedBy: currentDate,
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

    it('should delete a Sample', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSampleToCollectionIfMissing', () => {
      it('should add a Sample to an empty array', () => {
        const sample: ISample = { id: 'ABC' };
        expectedResult = service.addSampleToCollectionIfMissing([], sample);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sample);
      });

      it('should not add a Sample to an array that contains it', () => {
        const sample: ISample = { id: 'ABC' };
        const sampleCollection: ISample[] = [
          {
            ...sample,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addSampleToCollectionIfMissing(sampleCollection, sample);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Sample to an array that doesn't contain it", () => {
        const sample: ISample = { id: 'ABC' };
        const sampleCollection: ISample[] = [{ id: 'CBA' }];
        expectedResult = service.addSampleToCollectionIfMissing(sampleCollection, sample);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sample);
      });

      it('should add only unique Sample to an array', () => {
        const sampleArray: ISample[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'fab292bf-dd34-44cd-bb4f-5672c37cc37d' }];
        const sampleCollection: ISample[] = [{ id: 'ABC' }];
        expectedResult = service.addSampleToCollectionIfMissing(sampleCollection, ...sampleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sample: ISample = { id: 'ABC' };
        const sample2: ISample = { id: 'CBA' };
        expectedResult = service.addSampleToCollectionIfMissing([], sample, sample2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sample);
        expect(expectedResult).toContain(sample2);
      });

      it('should accept null and undefined values', () => {
        const sample: ISample = { id: 'ABC' };
        expectedResult = service.addSampleToCollectionIfMissing([], null, sample, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sample);
      });

      it('should return initial array if no Sample is added', () => {
        const sampleCollection: ISample[] = [{ id: 'ABC' }];
        expectedResult = service.addSampleToCollectionIfMissing(sampleCollection, undefined, null);
        expect(expectedResult).toEqual(sampleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
