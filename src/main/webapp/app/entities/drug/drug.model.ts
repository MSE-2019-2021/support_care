import { INotice } from 'app/entities/notice/notice.model';
import { IAdministration } from 'app/entities/administration/administration.model';
import { ITherapeuticRegime } from 'app/entities/therapeutic-regime/therapeutic-regime.model';

export interface IDrug {
  id?: number;
  name?: string;
  description?: string;
  notices?: INotice[];
  administration?: IAdministration;
  therapeuticRegimes?: ITherapeuticRegime[];
}

export class Drug implements IDrug {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public notices?: INotice[],
    public administration?: IAdministration,
    public therapeuticRegimes?: ITherapeuticRegime[]
  ) {}
}
