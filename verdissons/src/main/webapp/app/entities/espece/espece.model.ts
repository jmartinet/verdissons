import { IBotanicItem } from "../botanicItem/botanicItem.model";

export class Espece implements IBotanicItem {
  constructor(public id?: number, public nom?: string | null, public parent?: IBotanicItem | null) {}
}

export function getEspeceIdentifier(espece: IBotanicItem): number | undefined {
  return espece.id;
}
