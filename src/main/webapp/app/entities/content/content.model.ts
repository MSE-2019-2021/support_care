import { IDocument } from 'app/entities/document/document.model';

export interface IContent {
  id?: number;
  dataContentType?: string;
  data?: string;
  document?: IDocument;
}

export class Content implements IContent {
  constructor(public id?: number, public dataContentType?: string, public data?: string, public document?: IDocument) {}
}
