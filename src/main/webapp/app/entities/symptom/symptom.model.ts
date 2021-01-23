import { IToxicityRate } from 'app/entities/toxicity-rate/toxicity-rate.model';
import { ITherapeuticRegime } from 'app/entities/therapeutic-regime/therapeutic-regime.model';
import { IOutcome } from 'app/entities/outcome/outcome.model';
import * as dayjs from 'dayjs';

export interface ISymptom {
  id?: number;
  name?: string;
  notice?: string;
  toxicityRates?: IToxicityRate[];
  therapeuticRegimes?: ITherapeuticRegime[];
  outcomes?: IOutcome[];
  lastModifiedDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
}

export class Symptom implements ISymptom {
  constructor(
    public id?: number,
    public name?: string,
    public notice?: string,
    public toxicityRates?: IToxicityRate[],
    public therapeuticRegimes?: ITherapeuticRegime[],
    public outcomes?: IOutcome[],
    public lastModifiedDate?: dayjs.Dayjs | null,
    public createdDate?: dayjs.Dayjs | null
  ) {}
}
