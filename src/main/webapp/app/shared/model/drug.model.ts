import { INotice } from 'app/shared/model/notice.model';

export interface IDrug {
  id?: number;
  name?: string;
  description?: string;
  administrationType?: string;
  administrationId?: number;
  notices?: INotice[];
  therapeuticRegimeName?: string;
  therapeuticRegimeId?: number;
}

export class Drug implements IDrug {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public administrationType?: string,
    public administrationId?: number,
    public notices?: INotice[],
    public therapeuticRegimeName?: string,
    public therapeuticRegimeId?: number
  ) {}
}
