import { IDrug } from 'app/shared/model/drug.model';
import { ITreatment } from 'app/shared/model/treatment.model';
import { ISymptom } from 'app/shared/model/symptom.model';

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
  drugs?: IDrug[];
  treatment?: ITreatment;
  symptoms?: ISymptom[];
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
    public drugs?: IDrug[],
    public treatment?: ITreatment,
    public symptoms?: ISymptom[]
  ) {}
}
