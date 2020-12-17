export interface IContent {
  id?: number;
  data?: string;
}

export class Content implements IContent {
  constructor(public id?: number, public data?: string) {}
}
