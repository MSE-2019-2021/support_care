import { IDrug } from 'app/shared/model/drug.model';

export interface ITherapeuticRegime {
  id?: number;
  name?: string;
  acronym?: string;
  purpose?: string;
  condition?: string;
  timing?: string;
  indication?: string;
  criteria?: string;
  notice?: string;
  treatmentType?: string;
  treatmentId?: number;
  drugs?: IDrug[];
  diagnosticName?: string;
  diagnosticId?: number;
}

export class TherapeuticRegime implements ITherapeuticRegime {
  constructor(
    public id?: number,
    public name?: string,
    public acronym?: string,
    public purpose?: string,
    public condition?: string,
    public timing?: string,
    public indication?: string,
    public criteria?: string,
    public notice?: string,
    public treatmentType?: string,
    public treatmentId?: number,
    public drugs?: IDrug[],
    public diagnosticName?: string,
    public diagnosticId?: number
  ) {}
}
