import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';
import * as dayjs from 'dayjs';

export interface IFeedback {
  id?: number;
  entityType?: EntityFeedback;
  entityId?: number;
  entityName?: string | null;
  reason?: string | null;
  solved?: boolean;
  anonym?: boolean;
  createdBy?: string;
  createdDate?: dayjs.Dayjs | null;
}

export class Feedback implements IFeedback {
  constructor(
    public id?: number,
    public entityType?: EntityFeedback,
    public entityId?: number,
    public entityName?: string | null,
    public reason?: string | null,
    public solved?: boolean,
    public anonym?: boolean,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs | null
  ) {
    this.solved = this.solved ?? false;
    this.anonym = this.anonym ?? false;
  }
}
