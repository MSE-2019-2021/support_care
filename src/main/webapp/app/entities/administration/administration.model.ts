import { IDrug } from 'app/entities/drug/drug.model';

export interface IAdministration {
  id?: number;
  type?: string;
  drugs?: IDrug[];
}

export class Administration implements IAdministration {
  constructor(public id?: number, public type?: string, public drugs?: IDrug[]) {}
}
