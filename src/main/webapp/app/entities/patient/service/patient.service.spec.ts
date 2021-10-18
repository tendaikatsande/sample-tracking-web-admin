import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPatient, Patient } from '../patient.model';

import { PatientService } from './patient.service';

describe('Patient Service', () => {
  let service: PatientService;
  let httpMock: HttpTestingController;
  let elemDefault: IPatient;
  let expectedResult: IPatient | IPatient[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PatientService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      appId: 'AAAAAAA',
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      gender: 'AAAAAAA',
      dob: 'AAAAAAA',
      client: 'AAAAAAA',
      clientPatientId: 'AAAAAAA',
      cohortNumber: 'AAAAAAA',
      dateCreated: 'AAAAAAA',
      dateModified: 'AAAAAAA',
      phoneNumber: 'AAAAAAA',
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

    it('should create a Patient', () => {
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

      service.create(new Patient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Patient', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          appId: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          gender: 'BBBBBB',
          dob: 'BBBBBB',
          client: 'BBBBBB',
          clientPatientId: 'BBBBBB',
          cohortNumber: 'BBBBBB',
          dateCreated: 'BBBBBB',
          dateModified: 'BBBBBB',
          phoneNumber: 'BBBBBB',
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

    it('should partial update a Patient', () => {
      const patchObject = Object.assign(
        {
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          clientPatientId: 'BBBBBB',
          dateCreated: 'BBBBBB',
          createdDate: currentDate.format(DATE_TIME_FORMAT),
          lastModifiedBy: 'BBBBBB',
        },
        new Patient()
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

    it('should return a list of Patient', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          appId: 'BBBBBB',
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          gender: 'BBBBBB',
          dob: 'BBBBBB',
          client: 'BBBBBB',
          clientPatientId: 'BBBBBB',
          cohortNumber: 'BBBBBB',
          dateCreated: 'BBBBBB',
          dateModified: 'BBBBBB',
          phoneNumber: 'BBBBBB',
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

    it('should delete a Patient', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPatientToCollectionIfMissing', () => {
      it('should add a Patient to an empty array', () => {
        const patient: IPatient = { id: 'ABC' };
        expectedResult = service.addPatientToCollectionIfMissing([], patient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(patient);
      });

      it('should not add a Patient to an array that contains it', () => {
        const patient: IPatient = { id: 'ABC' };
        const patientCollection: IPatient[] = [
          {
            ...patient,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addPatientToCollectionIfMissing(patientCollection, patient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Patient to an array that doesn't contain it", () => {
        const patient: IPatient = { id: 'ABC' };
        const patientCollection: IPatient[] = [{ id: 'CBA' }];
        expectedResult = service.addPatientToCollectionIfMissing(patientCollection, patient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(patient);
      });

      it('should add only unique Patient to an array', () => {
        const patientArray: IPatient[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '56644172-e025-4e58-aefb-4934637ad74d' }];
        const patientCollection: IPatient[] = [{ id: 'ABC' }];
        expectedResult = service.addPatientToCollectionIfMissing(patientCollection, ...patientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const patient: IPatient = { id: 'ABC' };
        const patient2: IPatient = { id: 'CBA' };
        expectedResult = service.addPatientToCollectionIfMissing([], patient, patient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(patient);
        expect(expectedResult).toContain(patient2);
      });

      it('should accept null and undefined values', () => {
        const patient: IPatient = { id: 'ABC' };
        expectedResult = service.addPatientToCollectionIfMissing([], null, patient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(patient);
      });

      it('should return initial array if no Patient is added', () => {
        const patientCollection: IPatient[] = [{ id: 'ABC' }];
        expectedResult = service.addPatientToCollectionIfMissing(patientCollection, undefined, null);
        expect(expectedResult).toEqual(patientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
