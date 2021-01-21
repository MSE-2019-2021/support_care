import { IDocument } from 'app/entities/document/document.model';
import * as dayjs from 'dayjs';

export interface IOutcome {
  id?: number;
  name?: string;
  description?: string;
  link?: string;
  documents?: IDocument[];
  lastModifiedDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
}

export class Outcome implements IOutcome {
  constructor(
    public id?: number,
    public name?: string,
    public link?: string,
    public description?: string,
    public documents?: IDocument[],
    public lastModifiedDate?: dayjs.Dayjs | null,
    public createdDate?: dayjs.Dayjs | null
  ) {}
}
