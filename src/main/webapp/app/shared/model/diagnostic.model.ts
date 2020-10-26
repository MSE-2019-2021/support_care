import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';

export interface IDiagnostic {
  id?: number;
  name?: string;
  notice?: string;
  therapeuticRegimes?: ITherapeuticRegime[];
}

export class Diagnostic implements IDiagnostic {
  constructor(public id?: number, public name?: string, public notice?: string, public therapeuticRegimes?: ITherapeuticRegime[]) {}
}
