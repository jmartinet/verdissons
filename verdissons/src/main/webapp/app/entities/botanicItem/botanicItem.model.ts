export interface IBotanicItem {
  id?: number;
  nom?: string | null;
  type?: string | null;
  parent?: IBotanicItem | null;
  children?: Array<IBotanicItem> | null;
}

export class BotanicItem implements IBotanicItem {
  constructor(public id?: number, public nom?: string | null, public type?: string | null, public parent?: IBotanicItem | null, public children?: Array<IBotanicItem>  | null) {}
}

export function getBotanicItemIdentifier(item: IBotanicItem): number | undefined {
  return item.id;
}
