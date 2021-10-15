import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IClient, Client } from '../client.model';

import { ClientService } from './client.service';

describe('Client Service', () => {
  let service: ClientService;
  let httpMock: HttpTestingController;
  let elemDefault: IClient;
  let expectedResult: IClient | IClient[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ClientService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      clientUid: 'AAAAAAA',
      clientId: 'AAAAAAA',
      name: 'AAAAAAA',
      phone: 'AAAAAAA',
      districtId: 'AAAAAAA',
      districtName: 'AAAAAAA',
      provinceId: 'AAAAAAA',
      provinceName: 'AAAAAAA',
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

    it('should create a Client', () => {
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

      service.create(new Client()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Client', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          clientUid: 'BBBBBB',
          clientId: 'BBBBBB',
          name: 'BBBBBB',
          phone: 'BBBBBB',
          districtId: 'BBBBBB',
          districtName: 'BBBBBB',
          provinceId: 'BBBBBB',
          provinceName: 'BBBBBB',
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

    it('should partial update a Client', () => {
      const patchObject = Object.assign(
        {
          clientId: 'BBBBBB',
          name: 'BBBBBB',
          phone: 'BBBBBB',
          districtId: 'BBBBBB',
          districtName: 'BBBBBB',
          createdBy: 'BBBBBB',
          lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
        },
        new Client()
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

    it('should return a list of Client', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          clientUid: 'BBBBBB',
          clientId: 'BBBBBB',
          name: 'BBBBBB',
          phone: 'BBBBBB',
          districtId: 'BBBBBB',
          districtName: 'BBBBBB',
          provinceId: 'BBBBBB',
          provinceName: 'BBBBBB',
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

    it('should delete a Client', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addClientToCollectionIfMissing', () => {
      it('should add a Client to an empty array', () => {
        const client: IClient = { id: 123 };
        expectedResult = service.addClientToCollectionIfMissing([], client);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(client);
      });

      it('should not add a Client to an array that contains it', () => {
        const client: IClient = { id: 123 };
        const clientCollection: IClient[] = [
          {
            ...client,
          },
          { id: 456 },
        ];
        expectedResult = service.addClientToCollectionIfMissing(clientCollection, client);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Client to an array that doesn't contain it", () => {
        const client: IClient = { id: 123 };
        const clientCollection: IClient[] = [{ id: 456 }];
        expectedResult = service.addClientToCollectionIfMissing(clientCollection, client);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(client);
      });

      it('should add only unique Client to an array', () => {
        const clientArray: IClient[] = [{ id: 123 }, { id: 456 }, { id: 29664 }];
        const clientCollection: IClient[] = [{ id: 123 }];
        expectedResult = service.addClientToCollectionIfMissing(clientCollection, ...clientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const client: IClient = { id: 123 };
        const client2: IClient = { id: 456 };
        expectedResult = service.addClientToCollectionIfMissing([], client, client2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(client);
        expect(expectedResult).toContain(client2);
      });

      it('should accept null and undefined values', () => {
        const client: IClient = { id: 123 };
        expectedResult = service.addClientToCollectionIfMissing([], null, client, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(client);
      });

      it('should return initial array if no Client is added', () => {
        const clientCollection: IClient[] = [{ id: 123 }];
        expectedResult = service.addClientToCollectionIfMissing(clientCollection, undefined, null);
        expect(expectedResult).toEqual(clientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
