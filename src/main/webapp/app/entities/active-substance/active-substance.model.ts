import { INotice } from 'app/entities/notice/notice.model';
import { IAdministration } from 'app/entities/administration/administration.model';
import { ITherapeuticRegime } from 'app/entities/therapeutic-regime/therapeutic-regime.model';
import * as dayjs from 'dayjs';

export interface IActiveSubstance {
  id?: number;
  name?: string;
  dosage?: string;
  form?: string;
  description?: string;
  notices?: INotice[];
  administration?: IAdministration;
  therapeuticRegimes?: ITherapeuticRegime[];
  lastModifiedDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
}

export class ActiveSubstance implements IActiveSubstance {
  constructor(
    public id?: number,
    public name?: string,
    public dosage?: string,
    public form?: string,
    public description?: string,
    public notices?: INotice[],
    public administration?: IAdministration,
    public therapeuticRegimes?: ITherapeuticRegime[],
    public lastModifiedDate?: dayjs.Dayjs | null,
    public createdDate?: dayjs.Dayjs | null
  ) {}
}
