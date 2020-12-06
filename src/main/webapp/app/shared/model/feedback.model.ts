import { EntityFeedback } from 'app/shared/model/enumerations/entity-feedback.model';

export interface IFeedback {
  id?: number;
  entityName?: EntityFeedback;
  entityId?: number;
  thumb?: boolean;
  reason?: string;
  solved?: boolean;
  anonym?: boolean;
}

export class Feedback implements IFeedback {
  constructor(
    public id?: number,
    public entityName?: EntityFeedback,
    public entityId?: number,
    public thumb?: boolean,
    public reason?: string,
    public solved?: boolean,
    public anonym?: boolean
  ) {
    this.thumb = this.thumb ?? false;
    this.solved = this.solved ?? false;
    this.anonym = this.anonym ?? false;
  }
}
