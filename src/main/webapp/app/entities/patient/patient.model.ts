import * as dayjs from 'dayjs';

export interface IPatient {
  id?: number;
  appId?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  gender?: string | null;
  dob?: string | null;
  client?: string | null;
  clientPatientId?: string | null;
  cohortNumber?: string | null;
  dateCreated?: string | null;
  dateModified?: string | null;
  phoneNumber?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public appId?: string | null,
    public firstName?: string | null,
    public lastName?: string | null,
    public gender?: string | null,
    public dob?: string | null,
    public client?: string | null,
    public clientPatientId?: string | null,
    public cohortNumber?: string | null,
    public dateCreated?: string | null,
    public dateModified?: string | null,
    public phoneNumber?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {}
}

export function getPatientIdentifier(patient: IPatient): number | undefined {
  return patient.id;
}
