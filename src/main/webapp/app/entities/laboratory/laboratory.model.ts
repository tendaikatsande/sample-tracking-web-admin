import * as dayjs from 'dayjs';

export interface ILaboratory {
  id?: number;
  name?: string | null;
  type?: string | null;
  code?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class Laboratory implements ILaboratory {
  constructor(
    public id?: number,
    public name?: string | null,
    public type?: string | null,
    public code?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {}
}

export function getLaboratoryIdentifier(laboratory: ILaboratory): number | undefined {
  return laboratory.id;
}
