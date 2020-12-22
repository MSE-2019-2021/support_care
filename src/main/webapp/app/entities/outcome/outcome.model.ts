import { IDocument } from 'app/entities/document/document.model';
import { ISymptom } from 'app/entities/symptom/symptom.model';

export interface IOutcome {
  id?: number;
  name?: string;
  description?: string;
  documents?: IDocument[];
  symptoms?: ISymptom[];
}

export class Outcome implements IOutcome {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public documents?: IDocument[],
    public symptoms?: ISymptom[]
  ) {}
}
