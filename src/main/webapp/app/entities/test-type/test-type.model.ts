import * as dayjs from 'dayjs';

export interface ITestType {
  id?: string;
  name?: string | null;
  prefix?: string | null;
  createdBy?: string | null;
  modifiedBy?: string | null;
  dateCreated?: dayjs.Dayjs | null;
  dateModified?: dayjs.Dayjs | null;
}

export class TestType implements ITestType {
  constructor(
    public id?: string,
    public name?: string | null,
    public prefix?: string | null,
    public createdBy?: string | null,
    public modifiedBy?: string | null,
    public dateCreated?: dayjs.Dayjs | null,
    public dateModified?: dayjs.Dayjs | null
  ) {}
}

export function getTestTypeIdentifier(testType: ITestType): string | undefined {
  return testType.id;
}
