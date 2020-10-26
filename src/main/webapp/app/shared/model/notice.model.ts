export interface INotice {
  id?: number;
  description?: string;
  evaluation?: string;
  intervention?: string;
  drugName?: string;
  drugId?: number;
}

export class Notice implements INotice {
  constructor(
    public id?: number,
    public description?: string,
    public evaluation?: string,
    public intervention?: string,
    public drugName?: string,
    public drugId?: number
  ) {}
}
