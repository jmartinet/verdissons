import { IGenre } from "../genre/genre.model";

export interface IEspece {
  id?: number;
  nom?: string | null;
  parent?: IGenre | null;
}

export class Espece implements IEspece {
  constructor(public id?: number, public nom?: string | null, public genre?: IGenre | null) {}
}

export function getEspeceIdentifier(espece: IEspece): number | undefined {
  return espece.id;
}
