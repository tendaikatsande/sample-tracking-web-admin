import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IShipment, Shipment } from '../shipment.model';

import { ShipmentService } from './shipment.service';

describe('Shipment Service', () => {
  let service: ShipmentService;
  let httpMock: HttpTestingController;
  let elemDefault: IShipment;
  let expectedResult: IShipment | IShipment[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShipmentService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      appId: 'AAAAAAA',
      description: 'AAAAAAA',
      clientId: 'AAAAAAA',
      samples: 'AAAAAAA',
      status: 'AAAAAAA',
      dateCreated: 'AAAAAAA',
      dateModified: 'AAAAAAA',
      riderId: 'AAAAAAA',
      riderName: 'AAAAAAA',
      destination: 'AAAAAAA',
      clusterClientId: 'AAAAAAA',
      temperatureOrigin: 'AAAAAAA',
      temperatureDestination: 'AAAAAAA',
      isModifiedByHub: false,
      isModifiedByFacility: false,
      isModifiedByLaboratory: false,
      isModifiedByCourrier: false,
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

    it('should create a Shipment', () => {
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

      service.create(new Shipment()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Shipment', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          appId: 'BBBBBB',
          description: 'BBBBBB',
          clientId: 'BBBBBB',
          samples: 'BBBBBB',
          status: 'BBBBBB',
          dateCreated: 'BBBBBB',
          dateModified: 'BBBBBB',
          riderId: 'BBBBBB',
          riderName: 'BBBBBB',
          destination: 'BBBBBB',
          clusterClientId: 'BBBBBB',
          temperatureOrigin: 'BBBBBB',
          temperatureDestination: 'BBBBBB',
          isModifiedByHub: true,
          isModifiedByFacility: true,
          isModifiedByLaboratory: true,
          isModifiedByCourrier: true,
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

    it('should partial update a Shipment', () => {
      const patchObject = Object.assign(
        {
          appId: 'BBBBBB',
          description: 'BBBBBB',
          clientId: 'BBBBBB',
          dateCreated: 'BBBBBB',
          dateModified: 'BBBBBB',
          riderId: 'BBBBBB',
          riderName: 'BBBBBB',
          destination: 'BBBBBB',
          temperatureOrigin: 'BBBBBB',
          temperatureDestination: 'BBBBBB',
          isModifiedByFacility: true,
          isModifiedByLaboratory: true,
          isModifiedByCourrier: true,
          createdBy: 'BBBBBB',
        },
        new Shipment()
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

    it('should return a list of Shipment', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          appId: 'BBBBBB',
          description: 'BBBBBB',
          clientId: 'BBBBBB',
          samples: 'BBBBBB',
          status: 'BBBBBB',
          dateCreated: 'BBBBBB',
          dateModified: 'BBBBBB',
          riderId: 'BBBBBB',
          riderName: 'BBBBBB',
          destination: 'BBBBBB',
          clusterClientId: 'BBBBBB',
          temperatureOrigin: 'BBBBBB',
          temperatureDestination: 'BBBBBB',
          isModifiedByHub: true,
          isModifiedByFacility: true,
          isModifiedByLaboratory: true,
          isModifiedByCourrier: true,
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

    it('should delete a Shipment', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addShipmentToCollectionIfMissing', () => {
      it('should add a Shipment to an empty array', () => {
        const shipment: IShipment = { id: 'ABC' };
        expectedResult = service.addShipmentToCollectionIfMissing([], shipment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipment);
      });

      it('should not add a Shipment to an array that contains it', () => {
        const shipment: IShipment = { id: 'ABC' };
        const shipmentCollection: IShipment[] = [
          {
            ...shipment,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addShipmentToCollectionIfMissing(shipmentCollection, shipment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Shipment to an array that doesn't contain it", () => {
        const shipment: IShipment = { id: 'ABC' };
        const shipmentCollection: IShipment[] = [{ id: 'CBA' }];
        expectedResult = service.addShipmentToCollectionIfMissing(shipmentCollection, shipment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipment);
      });

      it('should add only unique Shipment to an array', () => {
        const shipmentArray: IShipment[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '522474f0-52a4-4c39-b65f-e34e4c07587b' }];
        const shipmentCollection: IShipment[] = [{ id: 'ABC' }];
        expectedResult = service.addShipmentToCollectionIfMissing(shipmentCollection, ...shipmentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shipment: IShipment = { id: 'ABC' };
        const shipment2: IShipment = { id: 'CBA' };
        expectedResult = service.addShipmentToCollectionIfMissing([], shipment, shipment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipment);
        expect(expectedResult).toContain(shipment2);
      });

      it('should accept null and undefined values', () => {
        const shipment: IShipment = { id: 'ABC' };
        expectedResult = service.addShipmentToCollectionIfMissing([], null, shipment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipment);
      });

      it('should return initial array if no Shipment is added', () => {
        const shipmentCollection: IShipment[] = [{ id: 'ABC' }];
        expectedResult = service.addShipmentToCollectionIfMissing(shipmentCollection, undefined, null);
        expect(expectedResult).toEqual(shipmentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
