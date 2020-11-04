import { INotice } from 'app/shared/model/notice.model';
import { IAdministration } from 'app/shared/model/administration.model';
import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';

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
