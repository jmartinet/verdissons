import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'famille',
        data: { pageTitle: 'Familles' },
        loadChildren: () => import('./famille/famille.module').then(m => m.FamilleModule),
      },
      {
        path: 'genre',
        data: { pageTitle: 'Genres' },
        loadChildren: () => import('./genre/genre.module').then(m => m.GenreModule),
      },
      {
        path: 'variete',
        data: { pageTitle: 'Varietes' },
        loadChildren: () => import('./variete/variete.module').then(m => m.VarieteModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
