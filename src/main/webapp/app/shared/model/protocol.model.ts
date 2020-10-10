import { Moment } from 'moment';

export interface IProtocol {
  id?: number;
  toxicityDiagnosis?: string;
  createUser?: string;
  createDate?: Moment;
  updateUser?: string;
  updateDate?: Moment;
  therapeuticRegimeId?: number;
  outcomeId?: number;
  guideId?: number;
}

export class Protocol implements IProtocol {
  constructor(
    public id?: number,
    public toxicityDiagnosis?: string,
    public createUser?: string,
    public createDate?: Moment,
    public updateUser?: string,
    public updateDate?: Moment,
    public therapeuticRegimeId?: number,
    public outcomeId?: number,
    public guideId?: number
  ) {}
}
