import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';

export interface IThumb {
  id?: number;
  entityType?: EntityFeedback;
  entityId?: number;
  thumb?: boolean;
}

export class Thumb implements IThumb {
  constructor(public id?: number, public entityType?: EntityFeedback, public entityId?: number, public thumb?: boolean) {}
}
