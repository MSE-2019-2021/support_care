import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';

export interface ITreatment {
  id?: number;
  type?: string;
  therapeuticRegimes?: ITherapeuticRegime[];
}

export class Treatment implements ITreatment {
  constructor(public id?: number, public type?: string, public therapeuticRegimes?: ITherapeuticRegime[]) {}
}
