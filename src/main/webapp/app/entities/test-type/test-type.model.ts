import * as dayjs from 'dayjs';

export interface ITestType {
  id?: number;
  name?: string | null;
  prefix?: string | null;
  createdBy?: string | null;
  modifiedBy?: string | null;
  dateCreated?: dayjs.Dayjs | null;
  dateModified?: dayjs.Dayjs | null;
}

export class TestType implements ITestType {
  constructor(
    public id?: number,
    public name?: string | null,
    public prefix?: string | null,
    public createdBy?: string | null,
    public modifiedBy?: string | null,
    public dateCreated?: dayjs.Dayjs | null,
    public dateModified?: dayjs.Dayjs | null
  ) {}
}

export function getTestTypeIdentifier(testType: ITestType): number | undefined {
  return testType.id;
}
