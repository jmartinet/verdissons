import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISemence, Semence } from '../semence.model';
import { SemenceService } from '../service/semence.service';

@Injectable({ providedIn: 'root' })
export class SemenceRoutingResolveService implements Resolve<ISemence> {
  constructor(protected service: SemenceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISemence> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((semence: HttpResponse<Semence>) => {
          if (semence.body) {
            return of(semence.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Semence());
  }
}
