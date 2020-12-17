import { IContent } from 'app/entities/content/content.model';

export interface IDocument {
  id?: number;
  title?: string;
  size?: number;
  mimeType?: string;
  content?: IContent;
}

export class Document implements IDocument {
  constructor(public id?: number, public title?: string, public size?: number, public mimeType?: string, public content?: IContent) {}
}
