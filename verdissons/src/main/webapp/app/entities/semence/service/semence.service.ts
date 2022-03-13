import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISemence, getSemenceIdentifier } from '../semence.model';

export type EntityResponseType = HttpResponse<ISemence>;
export type EntityArrayResponseType = HttpResponse<ISemence[]>;

@Injectable({ providedIn: 'root' })
export class SemenceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/semences');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(semence: ISemence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(semence);
    return this.http
      .post<ISemence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(semence: ISemence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(semence);
    return this.http
      .put<ISemence>(`${this.resourceUrl}/${getSemenceIdentifier(semence) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(semence: ISemence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(semence);
    return this.http
      .patch<ISemence>(`${this.resourceUrl}/${getSemenceIdentifier(semence) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISemence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISemence[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSemenceToCollectionIfMissing(semenceCollection: ISemence[], ...semencesToCheck: (ISemence | null | undefined)[]): ISemence[] {
    const semences: ISemence[] = semencesToCheck.filter(isPresent);
    if (semences.length > 0) {
      const semenceCollectionIdentifiers = semenceCollection.map(semenceItem => getSemenceIdentifier(semenceItem)!);
      const semencesToAdd = semences.filter(semenceItem => {
        const semenceIdentifier = getSemenceIdentifier(semenceItem);
        if (semenceIdentifier == null || semenceCollectionIdentifiers.includes(semenceIdentifier)) {
          return false;
        }
        semenceCollectionIdentifiers.push(semenceIdentifier);
        return true;
      });
      return [...semencesToAdd, ...semenceCollection];
    }
    return semenceCollection;
  }

  protected convertDateFromClient(semence: ISemence): ISemence {
    return Object.assign({}, semence, {
      periodeSemisDebut: semence.periodeSemisDebut?.isValid() ? semence.periodeSemisDebut.format(DATE_FORMAT) : undefined,
      periodeSemisFin: semence.periodeSemisFin?.isValid() ? semence.periodeSemisFin.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.periodeSemisDebut = res.body.periodeSemisDebut ? dayjs(res.body.periodeSemisDebut) : undefined;
      res.body.periodeSemisFin = res.body.periodeSemisFin ? dayjs(res.body.periodeSemisFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((semence: ISemence) => {
        semence.periodeSemisDebut = semence.periodeSemisDebut ? dayjs(semence.periodeSemisDebut) : undefined;
        semence.periodeSemisFin = semence.periodeSemisFin ? dayjs(semence.periodeSemisFin) : undefined;
      });
    }
    return res;
  }
}
