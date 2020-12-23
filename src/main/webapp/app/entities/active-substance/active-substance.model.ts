import { INotice } from 'app/entities/notice/notice.model';
import { IAdministration } from 'app/entities/administration/administration.model';
import { ITherapeuticRegime } from 'app/entities/therapeutic-regime/therapeutic-regime.model';

export interface IActiveSubstance {
  id?: number;
  name?: string;
  dosage?: string;
  form?: string;
  description?: string;
  notices?: INotice[];
  administration?: IAdministration;
  therapeuticRegimes?: ITherapeuticRegime[];
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
    public therapeuticRegimes?: ITherapeuticRegime[]
  ) {}
}
