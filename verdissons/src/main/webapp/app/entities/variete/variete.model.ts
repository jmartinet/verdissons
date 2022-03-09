import { IGenre } from 'app/entities/genre/genre.model';

export interface IVariete {
  id?: number;
  nomLatin?: string | null;
  conseilCulture?: string | null;
  culture?: string | null;
  exposition?: string | null;
  besoinEau?: string | null;
  natureSol?: string | null;
  qualiteSol?: string | null;
  genre?: IGenre | null;
}

export class Variete implements IVariete {
  constructor(
    public id?: number,
    public nomLatin?: string | null,
    public conseilCulture?: string | null,
    public culture?: string | null,
    public exposition?: string | null,
    public besoinEau?: string | null,
    public natureSol?: string | null,
    public qualiteSol?: string | null,
    public genre?: IGenre | null
  ) {}
}

export function getVarieteIdentifier(variete: IVariete): number | undefined {
  return variete.id;
}
