import { IDocument } from 'app/entities/document/document.model';

export interface IOutcome {
  id?: number;
  name?: string;
  description?: string;
  link?: string;
  documents?: IDocument[];
}

export class Outcome implements IOutcome {
  constructor(
    public id?: number,
    public name?: string,
    public link?: string,
    public description?: string,
    public documents?: IDocument[]
  ) {}
}
