import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getFamilleIdentifier } from '../famille.model';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';

export type EntityResponseType = HttpResponse<IBotanicItem>;
export type EntityArrayResponseType = HttpResponse<IBotanicItem[]>;

@Injectable({ providedIn: 'root' })
export class FamilleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/familles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(famille: IBotanicItem): Observable<EntityResponseType> {
    return this.http.post<IBotanicItem>(this.resourceUrl, famille, { observe: 'response' });
  }

  update(famille: IBotanicItem): Observable<EntityResponseType> {
    return this.http.put<IBotanicItem>(`${this.resourceUrl}/${getFamilleIdentifier(famille) as number}`, famille, { observe: 'response' });
  }

  partialUpdate(famille: IBotanicItem): Observable<EntityResponseType> {
    return this.http.patch<IBotanicItem>(`${this.resourceUrl}/${getFamilleIdentifier(famille) as number}`, famille, { observe: 'response' });
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

  addFamilleToCollectionIfMissing(familleCollection: IBotanicItem[], ...famillesToCheck: (IBotanicItem | null | undefined)[]): IBotanicItem[] {
    const familles: IBotanicItem[] = famillesToCheck.filter(isPresent);
    if (familles.length > 0) {
      const familleCollectionIdentifiers = familleCollection.map(familleItem => getFamilleIdentifier(familleItem)!);
      const famillesToAdd = familles.filter(familleItem => {
        const familleIdentifier = getFamilleIdentifier(familleItem);
        if (familleIdentifier == null || familleCollectionIdentifiers.includes(familleIdentifier)) {
          return false;
        }
        familleCollectionIdentifiers.push(familleIdentifier);
        return true;
      });
      return [...famillesToAdd, ...familleCollection];
    }
    return familleCollection;
  }
}
