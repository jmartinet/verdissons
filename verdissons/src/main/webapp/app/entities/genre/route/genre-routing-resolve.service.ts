import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Genre } from '../genre.model';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';
import { BotanicItemService } from 'app/entities/botanicItem/service/botanicItem.service';

@Injectable({ providedIn: 'root' })
export class GenreRoutingResolveService implements Resolve<IBotanicItem> {
  constructor(protected service: BotanicItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBotanicItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.findGenre(id).pipe(
        mergeMap((genre: HttpResponse<Genre>) => {
          if (genre.body) {
            return of(genre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Genre());
  }
}
