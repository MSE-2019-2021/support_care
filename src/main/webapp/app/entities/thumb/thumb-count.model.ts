export interface IThumbCount {
  countThumbUp?: number;
  countThumbDown?: number;
  thumb?: boolean;
}

export class ThumbCount implements IThumbCount {
  constructor(public countThumbUp?: number, public countThumbDown?: number, public thumb?: boolean) {
    this.thumb = this.thumb ?? undefined;
  }
}
