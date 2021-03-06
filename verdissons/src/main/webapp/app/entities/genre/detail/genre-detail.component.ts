import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';

@Component({
  selector: 'jhi-genre-detail',
  templateUrl: './genre-detail.component.html',
})
export class GenreDetailComponent implements OnInit {
  genre: IBotanicItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ genre }) => {
      this.genre = genre;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
