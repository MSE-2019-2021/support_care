export interface ITreatment {
  id?: number;
  type?: string;
}

export class Treatment implements ITreatment {
  constructor(public id?: number, public type?: string) {}
}
