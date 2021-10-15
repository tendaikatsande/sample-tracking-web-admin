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
    public createdBy?: string | null,
    public modifiedBy?: dayjs.Dayjs | null
  ) {}
}

export function getShipmentIdentifier(shipment: IShipment): string | undefined {
  return shipment.id;
}
