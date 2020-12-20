import { IActiveSubstance } from 'app/entities/active-substance/active-substance.model';

export interface IAdministration {
  id?: number;
  type?: string;
  activeSubstances?: IActiveSubstance[];
}

export class Administration implements IAdministration {
  constructor(public id?: number, public type?: string, public activeSubstances?: IActiveSubstance[]) {}
}
