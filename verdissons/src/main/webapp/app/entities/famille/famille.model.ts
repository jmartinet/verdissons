export interface IFamille {
  id?: number;
  nom?: string | null;
}

export class Famille implements IFamille {
  constructor(public id?: number, public nom?: string | null) {}
}

export function getFamilleIdentifier(famille: IFamille): number | undefined {
  return famille.id;
}
