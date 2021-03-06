import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getEspeceIdentifier } from '../espece.model';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';

export type EntityResponseType = HttpResponse<IBotanicItem>;
export type EntityArrayResponseType = HttpResponse<IBotanicItem[]>;

@Injectable({ providedIn: 'root' })
export class EspeceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/especes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(espece: IBotanicItem): Observable<EntityResponseType> {
    return this.http.post<IBotanicItem>(this.resourceUrl, espece, { observe: 'response' });
  }

  update(espece: IBotanicItem): Observable<EntityResponseType> {
    return this.http.put<IBotanicItem>(`${this.resourceUrl}/${getEspeceIdentifier(espece) as number}`, espece, { observe: 'response' });
  }

  partialUpdate(espece: IBotanicItem): Observable<EntityResponseType> {
    return this.http.patch<IBotanicItem>(`${this.resourceUrl}/${getEspeceIdentifier(espece) as number}`, espece, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBotanicItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBotanicItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEspeceToCollectionIfMissing(especeCollection: IBotanicItem[], ...especesToCheck: (IBotanicItem | null | undefined)[]): IBotanicItem[] {
    const especes: IBotanicItem[] = especesToCheck.filter(isPresent);
    if (especes.length > 0) {
      const especeCollectionIdentifiers = especeCollection.map(especeItem => getEspeceIdentifier(especeItem)!);
      const especesToAdd = especes.filter(especeItem => {
        const especeIdentifier = getEspeceIdentifier(especeItem);
        if (especeIdentifier == null || especeCollectionIdentifiers.includes(especeIdentifier)) {
          return false;
        }
        especeCollectionIdentifiers.push(especeIdentifier);
        return true;
      });
      return [...especesToAdd, ...especeCollection];
    }
    return especeCollection;
  }
}
