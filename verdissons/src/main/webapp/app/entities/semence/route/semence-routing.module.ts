import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SemenceComponent } from '../list/semence.component';
import { SemenceDetailComponent } from '../detail/semence-detail.component';
import { SemenceUpdateComponent } from '../update/semence-update.component';
import { SemenceRoutingResolveService } from './semence-routing-resolve.service';

const semenceRoute: Routes = [
  {
    path: '',
    component: SemenceComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SemenceDetailComponent,
    resolve: {
      semence: SemenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SemenceUpdateComponent,
    resolve: {
      semence: SemenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SemenceUpdateComponent,
    resolve: {
      semence: SemenceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(semenceRoute)],
  exports: [RouterModule],
})
export class SemenceRoutingModule {}
