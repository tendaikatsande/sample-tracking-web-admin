import * as dayjs from 'dayjs';

export interface ISampleType {
  id?: number;
  name?: string | null;
  prefix?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class SampleType implements ISampleType {
  constructor(
    public id?: number,
    public name?: string | null,
    public prefix?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {}
}

export function getSampleTypeIdentifier(sampleType: ISampleType): number | undefined {
  return sampleType.id;
}
