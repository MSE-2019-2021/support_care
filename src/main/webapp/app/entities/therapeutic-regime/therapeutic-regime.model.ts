import { IActiveSubstance } from 'app/entities/active-substance/active-substance.model';
import { ITreatment } from 'app/entities/treatment/treatment.model';
import { ISymptom } from 'app/entities/symptom/symptom.model';

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
  activeSubstances?: IActiveSubstance[];
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
    public activeSubstances?: IActiveSubstance[],
    public treatment?: ITreatment,
    public symptoms?: ISymptom[]
  ) {}
}
