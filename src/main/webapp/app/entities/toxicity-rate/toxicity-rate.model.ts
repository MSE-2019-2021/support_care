import { ISymptom } from 'app/entities/symptom/symptom.model';

export interface IToxicityRate {
  id?: number;
  name?: string;
  description?: string;
  notice?: string;
  autonomousIntervention?: string;
  interdependentIntervention?: string;
  selfManagement?: string;
  symptom?: ISymptom;
}

export class ToxicityRate implements IToxicityRate {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public notice?: string,
    public autonomousIntervention?: string,
    public interdependentIntervention?: string,
    public selfManagement?: string,
    public symptom?: ISymptom
  ) {}
}
