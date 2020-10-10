import { Moment } from 'moment';

export interface IDrug {
  id?: number;
  drugName?: string;
  createUser?: string;
  createDate?: Moment;
  updateUser?: string;
  updateDate?: Moment;
}

export class Drug implements IDrug {
  constructor(
    public id?: number,
    public drugName?: string,
    public createUser?: string,
    public createDate?: Moment,
    public updateUser?: string,
    public updateDate?: Moment
  ) {}
}
