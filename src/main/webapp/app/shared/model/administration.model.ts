export interface IAdministration {
  id?: number;
  type?: string;
}

export class Administration implements IAdministration {
  constructor(public id?: number, public type?: string) {}
}
