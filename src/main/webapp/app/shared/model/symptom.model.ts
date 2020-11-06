import { ITherapeuticRegime } from 'app/shared/model/therapeutic-regime.model';
import { IOutcome } from 'app/shared/model/outcome.model';
import { IToxicityRate } from 'app/shared/model/toxicity-rate.model';

export interface ISymptom {
  id?: number;
  name?: string;
  notice?: string;
  therapeuticRegimes?: ITherapeuticRegime[];
  outcomes?: IOutcome[];
  toxicityRates?: IToxicityRate[];
}

export class Symptom implements ISymptom {
  constructor(
    public id?: number,
    public name?: string,
    public notice?: string,
    public therapeuticRegimes?: ITherapeuticRegime[],
    public outcomes?: IOutcome[],
    public toxicityRates?: IToxicityRate[]
  ) {}
}
