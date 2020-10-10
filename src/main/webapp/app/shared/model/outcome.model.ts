import { Moment } from 'moment';

export interface IOutcome {
  id?: number;
  description?: string;
  score?: number;
  createUser?: string;
  createDate?: Moment;
  updateUser?: string;
  updateDate?: Moment;
}

export class Outcome implements IOutcome {
  constructor(
    public id?: number,
    public description?: string,
    public score?: number,
    public createUser?: string,
    public createDate?: Moment,
    public updateUser?: string,
    public updateDate?: Moment
  ) {}
}
