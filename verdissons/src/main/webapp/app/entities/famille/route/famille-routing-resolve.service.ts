import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Famille } from '../famille.model';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';
import { BotanicItemService } from 'app/entities/botanicItem/service/botanicItem.service';

@Injectable({ providedIn: 'root' })
export class FamilleRoutingResolveService implements Resolve<IBotanicItem> {
  constructor(protected service: BotanicItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBotanicItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((famille: HttpResponse<Famille>) => {
          if (famille.body) {
            return of(famille.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Famille());
  }
}
