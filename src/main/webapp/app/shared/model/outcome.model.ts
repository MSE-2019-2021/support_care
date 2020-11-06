import { IDocument } from 'app/shared/model/document.model';
import { ISymptom } from 'app/shared/model/symptom.model';

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
