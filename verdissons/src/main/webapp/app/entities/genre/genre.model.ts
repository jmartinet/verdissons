import { IFamille } from 'app/entities/famille/famille.model';

export interface IGenre {
  id?: number;
  nom?: string | null;
  parent?: IFamille | null;
}

export class Genre implements IGenre {
  constructor(public id?: number, public nom?: string | null, public famille?: IFamille | null) {}
}

export function getGenreIdentifier(genre: IGenre): number | undefined {
  return genre.id;
}
