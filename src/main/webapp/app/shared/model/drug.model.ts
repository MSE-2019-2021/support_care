import { INotice } from 'app/shared/model/notice.model';
import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';

export interface IDrug {
  id?: number;
  name?: string;
  description?: string;
  notices?: INotice[];
  administrationType?: string;
  administrationId?: number;
  therapeuticRegimes?: ITherapeuticRegime[];
}

export class Drug implements IDrug {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public notices?: INotice[],
    public administrationType?: string,
    public administrationId?: number,
    public therapeuticRegimes?: ITherapeuticRegime[]
  ) {}
}
