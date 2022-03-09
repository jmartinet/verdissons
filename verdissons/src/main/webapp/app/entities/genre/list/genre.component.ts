import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGenre } from '../genre.model';
import { GenreService } from '../service/genre.service';
import { GenreDeleteDialogComponent } from '../delete/genre-delete-dialog.component';

@Component({
  selector: 'jhi-genre',
  templateUrl: './genre.component.html',
})
export class GenreComponent implements OnInit {
  genres?: IGenre[];
  isLoading = false;

  constructor(protected genreService: GenreService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.genreService.query().subscribe({
      next: (res: HttpResponse<IGenre[]>) => {
        this.isLoading = false;
        this.genres = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IGenre): number {
    return item.id!;
  }

  delete(genre: IGenre): void {
    const modalRef = this.modalService.open(GenreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.genre = genre;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
