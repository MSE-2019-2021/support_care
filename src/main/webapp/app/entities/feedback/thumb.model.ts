export interface IThumb {
  countThumbUp?: number;
  countThumbDown?: number;
  thumb?: boolean;
}

export class Thumb implements IThumb {
  constructor(public countThumbUp?: number, public countThumbDown?: number, public thumb?: boolean) {
    this.thumb = this.thumb ?? undefined;
  }
}
