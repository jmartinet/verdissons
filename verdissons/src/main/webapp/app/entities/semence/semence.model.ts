import dayjs from 'dayjs/esm';
import { IVariete } from 'app/entities/variete/variete.model';

export interface ISemence {
  id?: number;
  typeSemis?: string | null;
  conseilSemis?: string | null;
  periodeSemisDebut?: dayjs.Dayjs | null;
  periodeSemisFin?: dayjs.Dayjs | null;
  variete?: IVariete | null;
}

export class Semence implements ISemence {
  constructor(
    public id?: number,
    public typeSemis?: string | null,
    public conseilSemis?: string | null,
    public periodeSemisDebut?: dayjs.Dayjs | null,
    public periodeSemisFin?: dayjs.Dayjs | null,
    public variete?: IVariete | null
  ) {}
}

export function getSemenceIdentifier(semence: ISemence): number | undefined {
  return semence.id;
}
