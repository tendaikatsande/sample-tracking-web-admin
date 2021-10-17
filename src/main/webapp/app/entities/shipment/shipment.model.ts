import * as dayjs from 'dayjs';

export interface IShipment {
  id?: string;
  appId?: string | null;
  description?: string | null;
  clientId?: string | null;
  samples?: string | null;
  status?: string | null;
  dateCreated?: string | null;
  dateModified?: dayjs.Dayjs | null;
  riderId?: string | null;
  riderName?: string | null;
  destination?: string | null;
  clusterClientId?: string | null;
  temperatureOrigin?: string | null;
  temperatureDestination?: string | null;
  isModifiedByHub?: boolean | null;
  isModifiedByFacility?: boolean | null;
  isModifiedByLaboratory?: boolean | null;
  isModifiedByCourrier?: boolean | null;
  createdBy?: string | null;
  modifiedBy?: dayjs.Dayjs | null;
}

export class Shipment implements IShipment {
  constructor(
    public id?: string,
    public appId?: string | null,
    public description?: string | null,
    public clientId?: string | null,
    public samples?: string | null,
    public status?: string | null,
    public dateCreated?: string | null,
    public dateModified?: dayjs.Dayjs | null,
    public riderId?: string | null,
    public riderName?: string | null,
    public destination?: string | null,
    public clusterClientId?: string | null,
    public temperatureOrigin?: string | null,
    public temperatureDestination?: string | null,
    public isModifiedByHub?: boolean | null,
    public isModifiedByFacility?: boolean | null,
    public isModifiedByLaboratory?: boolean | null,
    public isModifiedByCourrier?: boolean | null,
    public createdBy?: string | null,
    public modifiedBy?: dayjs.Dayjs | null
  ) {
    this.isModifiedByHub = this.isModifiedByHub ?? false;
    this.isModifiedByFacility = this.isModifiedByFacility ?? false;
    this.isModifiedByLaboratory = this.isModifiedByLaboratory ?? false;
    this.isModifiedByCourrier = this.isModifiedByCourrier ?? false;
  }
}

export function getShipmentIdentifier(shipment: IShipment): string | undefined {
  return shipment.id;
}
