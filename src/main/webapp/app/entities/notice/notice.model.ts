import { IActiveSubstance } from 'app/entities/active-substance/active-substance.model';

export interface INotice {
  id?: number;
  description?: string;
  evaluation?: string;
  intervention?: string;
  activeSubstance?: IActiveSubstance;
}

export class Notice implements INotice {
  constructor(
    public id?: number,
    public description?: string,
    public evaluation?: string,
    public intervention?: string,
    public activeSubstance?: IActiveSubstance
  ) {}
}
