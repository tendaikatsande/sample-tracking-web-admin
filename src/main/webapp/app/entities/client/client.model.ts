import * as dayjs from 'dayjs';

export interface IClient {
  id?: number;
  clientUid?: string | null;
  clientId?: string | null;
  name?: string | null;
  phone?: string | null;
  districtId?: string | null;
  districtName?: string | null;
  provinceId?: string | null;
  provinceName?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public clientUid?: string | null,
    public clientId?: string | null,
    public name?: string | null,
    public phone?: string | null,
    public districtId?: string | null,
    public districtName?: string | null,
    public provinceId?: string | null,
    public provinceName?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {}
}

export function getClientIdentifier(client: IClient): number | undefined {
  return client.id;
}
