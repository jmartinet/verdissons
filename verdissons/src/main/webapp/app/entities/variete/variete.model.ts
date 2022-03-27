import { IBotanicItem } from "../botanicItem/botanicItem.model";

export interface IVariete {
  id?: number;
  nom?: string | null;
  conseilCulture?: string | null;
  culture?: string | null;
  exposition?: string | null;
  besoinEau?: string | null;
  natureSol?: string | null;
  qualiteSol?: string | null;
  espece?: IBotanicItem | null;
}

export class Variete implements IVariete {
  constructor(
    public id?: number,
    public nom?: string | null,
    public conseilCulture?: string | null,
    public culture?: string | null,
    public exposition?: string | null,
    public besoinEau?: string | null,
    public natureSol?: string | null,
    public qualiteSol?: string | null,
    public espece?: IBotanicItem | null
  ) {}
}

export function getVarieteIdentifier(variete: IVariete): number | undefined {
  return variete.id;
}
