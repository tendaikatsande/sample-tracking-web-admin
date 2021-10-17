import * as dayjs from 'dayjs';

export interface ISample {
  id?: string;
  appId?: string | null;
  clientSampleId?: string | null;
  clientPatientId?: string | null;
  labId?: string | null;
  clientId?: string | null;
  sampleType?: string | null;
  testId?: string | null;
  dateCollected?: dayjs.Dayjs | null;
  collectedBy?: string | null;
  status?: string | null;
  comment?: string | null;
  synced?: boolean | null;
  dateSynced?: dayjs.Dayjs | null;
  labReferenceId?: string | null;
  location?: string | null;
  result?: string | null;
  resultReceivedBy?: string | null;
  shipmentId?: string | null;
  clientContact?: string | null;
  temperatureAtHub?: string | null;
  temperatureAtLab?: string | null;
  isModifiedByHub?: boolean | null;
  isModifiedByFacility?: boolean | null;
  isModifiedByLaboratory?: boolean | null;
  isModifiedByCourrier?: boolean | null;
  createdBy?: string | null;
  modifiedBy?: dayjs.Dayjs | null;
  dateCreated?: string | null;
  dateModified?: dayjs.Dayjs | null;
}

export class Sample implements ISample {
  constructor(
    public id?: string,
    public appId?: string | null,
    public clientSampleId?: string | null,
    public clientPatientId?: string | null,
    public labId?: string | null,
    public clientId?: string | null,
    public sampleType?: string | null,
    public testId?: string | null,
    public dateCollected?: dayjs.Dayjs | null,
    public collectedBy?: string | null,
    public status?: string | null,
    public comment?: string | null,
    public synced?: boolean | null,
    public dateSynced?: dayjs.Dayjs | null,
    public labReferenceId?: string | null,
    public location?: string | null,
    public result?: string | null,
    public resultReceivedBy?: string | null,
    public shipmentId?: string | null,
    public clientContact?: string | null,
    public temperatureAtHub?: string | null,
    public temperatureAtLab?: string | null,
    public isModifiedByHub?: boolean | null,
    public isModifiedByFacility?: boolean | null,
    public isModifiedByLaboratory?: boolean | null,
    public isModifiedByCourrier?: boolean | null,
    public createdBy?: string | null,
    public modifiedBy?: dayjs.Dayjs | null,
    public dateCreated?: string | null,
    public dateModified?: dayjs.Dayjs | null
  ) {
    this.synced = this.synced ?? false;
    this.isModifiedByHub = this.isModifiedByHub ?? false;
    this.isModifiedByFacility = this.isModifiedByFacility ?? false;
    this.isModifiedByLaboratory = this.isModifiedByLaboratory ?? false;
    this.isModifiedByCourrier = this.isModifiedByCourrier ?? false;
  }
}

export function getSampleIdentifier(sample: ISample): string | undefined {
  return sample.id;
}
