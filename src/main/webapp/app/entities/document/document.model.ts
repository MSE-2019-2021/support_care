import { IContent } from 'app/entities/content/content.model';
import { IOutcome } from 'app/entities/outcome/outcome.model';

export interface IDocument {
  id?: number;
  title?: string;
  size?: number;
  mimeType?: string;
  content?: IContent;
  outcome?: IOutcome;
}

export class Document implements IDocument {
  constructor(
    public id?: number,
    public title?: string,
    public size?: number,
    public mimeType?: string,
    public content?: IContent,
    public outcome?: IOutcome
  ) {}
}
