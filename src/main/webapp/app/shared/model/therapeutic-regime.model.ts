import { Moment } from 'moment';

export interface ITherapeuticRegime {
  id?: number;
  timing?: string;
  dietary?: string;
  sideEffects?: string;
  createUser?: string;
  createDate?: Moment;
  updateUser?: string;
  updateDate?: Moment;
  drugId?: number;
}

export class TherapeuticRegime implements ITherapeuticRegime {
  constructor(
    public id?: number,
    public timing?: string,
    public dietary?: string,
    public sideEffects?: string,
    public createUser?: string,
    public createDate?: Moment,
    public updateUser?: string,
    public updateDate?: Moment,
    public drugId?: number
  ) {}
}
