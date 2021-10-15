import * as dayjs from 'dayjs';

export interface IPatient {
  id?: string;
  appId?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  gender?: string | null;
  dob?: string | null;
  client?: string | null;
  clientPatientId?: string | null;
  cohortNumber?: string | null;
  dateCreated?: string | null;
  dateModified?: dayjs.Dayjs | null;
  phoneNumber?: string | null;
  createdBy?: string | null;
  modifiedBy?: dayjs.Dayjs | null;
}

export class Patient implements IPatient {
  constructor(
    public id?: string,
    public appId?: string | null,
    public firstName?: string | null,
    public lastName?: string | null,
    public gender?: string | null,
    public dob?: string | null,
    public client?: string | null,
    public clientPatientId?: string | null,
    public cohortNumber?: string | null,
    public dateCreated?: string | null,
    public dateModified?: dayjs.Dayjs | null,
    public phoneNumber?: string | null,
    public createdBy?: string | null,
    public modifiedBy?: dayjs.Dayjs | null
  ) {}
}

export function getPatientIdentifier(patient: IPatient): string | undefined {
  return patient.id;
}
