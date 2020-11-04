import { IDrug } from 'app/shared/model/drug.model';

export interface INotice {
  id?: number;
  description?: string;
  evaluation?: string;
  intervention?: string;
  drug?: IDrug;
}

export class Notice implements INotice {
  constructor(
    public id?: number,
    public description?: string,
    public evaluation?: string,
    public intervention?: string,
    public drug?: IDrug
  ) {}
}
