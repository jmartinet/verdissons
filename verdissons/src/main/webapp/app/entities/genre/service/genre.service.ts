import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getGenreIdentifier } from '../genre.model';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';

export type EntityResponseType = HttpResponse<IBotanicItem>;
export type EntityArrayResponseType = HttpResponse<IBotanicItem[]>;

@Injectable({ providedIn: 'root' })
export class GenreService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/genres');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(genre: IBotanicItem): Observable<EntityResponseType> {
    return this.http.post<IBotanicItem>(this.resourceUrl, genre, { observe: 'response' });
  }

  update(genre: IBotanicItem): Observable<EntityResponseType> {
    return this.http.put<IBotanicItem>(`${this.resourceUrl}/${getGenreIdentifier(genre) as number}`, genre, { observe: 'response' });
  }

  partialUpdate(genre: IBotanicItem): Observable<EntityResponseType> {
    return this.http.patch<IBotanicItem>(`${this.resourceUrl}/${getGenreIdentifier(genre) as number}`, genre, { observe: 'response' });
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

  addGenreToCollectionIfMissing(genreCollection: IBotanicItem[], ...genresToCheck: (IBotanicItem | null | undefined)[]): IBotanicItem[] {
    const genres: IBotanicItem[] = genresToCheck.filter(isPresent);
    if (genres.length > 0) {
      const genreCollectionIdentifiers = genreCollection.map(genreItem => getGenreIdentifier(genreItem)!);
      const genresToAdd = genres.filter(genreItem => {
        const genreIdentifier = getGenreIdentifier(genreItem);
        if (genreIdentifier == null || genreCollectionIdentifiers.includes(genreIdentifier)) {
          return false;
        }
        genreCollectionIdentifiers.push(genreIdentifier);
        return true;
      });
      return [...genresToAdd, ...genreCollection];
    }
    return genreCollection;
  }
}
