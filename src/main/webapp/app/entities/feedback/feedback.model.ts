import { EntityFeedback } from 'app/entities/enumerations/entity-feedback.model';
import * as dayjs from 'dayjs';

export interface IFeedback {
  id?: number;
  entityName?: EntityFeedback;
  entityId?: number;
  thumb?: boolean;
  reason?: string;
  solved?: boolean;
  anonym?: boolean;
  createdBy?: string;
  createdDate?: dayjs.Dayjs | null;
}

export class Feedback implements IFeedback {
  constructor(
    public id?: number,
    public entityName?: EntityFeedback,
    public entityId?: number,
    public thumb?: boolean,
    public reason?: string,
    public solved?: boolean,
    public anonym?: boolean,
    public createdBy?: string,
    public createdDate?: dayjs.Dayjs | null
  ) {
    this.thumb = this.thumb ?? false;
    this.solved = this.solved ?? false;
    this.anonym = this.anonym ?? false;
  }
}
