import { IBotanicItem } from "../botanicItem/botanicItem.model";

export class Famille implements IBotanicItem {
  constructor(public id?: number, public nom?: string | null) {}
}

export function getFamilleIdentifier(famille: IBotanicItem): number | undefined {
  return famille.id;
}
