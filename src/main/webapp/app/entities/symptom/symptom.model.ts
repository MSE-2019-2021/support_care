import { IToxicityRate } from 'app/entities/toxicity-rate/toxicity-rate.model';
import { ITherapeuticRegime } from 'app/entities/therapeutic-regime/therapeutic-regime.model';
import { IOutcome } from 'app/entities/outcome/outcome.model';

export interface ISymptom {
  id?: number;
  name?: string;
  notice?: string;
  toxicityRates?: IToxicityRate[];
  therapeuticRegimes?: ITherapeuticRegime[];
  outcomes?: IOutcome[];
}

export class Symptom implements ISymptom {
  constructor(
    public id?: number,
    public name?: string,
    public notice?: string,
    public toxicityRates?: IToxicityRate[],
    public therapeuticRegimes?: ITherapeuticRegime[],
    public outcomes?: IOutcome[]
  ) {}
}
