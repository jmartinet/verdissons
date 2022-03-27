import { IBotanicItem } from '../botanicItem/botanicItem.model';

export class Genre implements IBotanicItem {
  constructor(public id?: number, public nom?: string | null, public parent?: IBotanicItem | null) {}
}

export function getGenreIdentifier(genre: IBotanicItem): number | undefined {
  return genre.id;
}
