import { Moment } from 'moment';

export interface IGuide {
  id?: number;
  description?: string;
  createUser?: string;
  createDate?: Moment;
  updateUser?: string;
  updateDate?: Moment;
}

export class Guide implements IGuide {
  constructor(
    public id?: number,
    public description?: string,
    public createUser?: string,
    public createDate?: Moment,
    public updateUser?: string,
    public updateDate?: Moment
  ) {}
}
