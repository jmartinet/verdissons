import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IGenre, Genre } from '../genre.model';
import { GenreService } from '../service/genre.service';
import { IFamille } from 'app/entities/famille/famille.model';
import { FamilleService } from 'app/entities/famille/service/famille.service';

@Component({
  selector: 'jhi-genre-update',
  templateUrl: './genre-update.component.html',
})
export class GenreUpdateComponent implements OnInit {
  isSaving = false;

  famillesSharedCollection: IFamille[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    famille: [],
  });

  constructor(
    protected genreService: GenreService,
    protected familleService: FamilleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ genre }) => {
      this.updateForm(genre);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const genre = this.createFromForm();
    if (genre.id !== undefined) {
      this.subscribeToSaveResponse(this.genreService.update(genre));
    } else {
      this.subscribeToSaveResponse(this.genreService.create(genre));
    }
  }

  trackFamilleById(index: number, item: IFamille): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGenre>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(genre: IGenre): void {
    this.editForm.patchValue({
      id: genre.id,
      nom: genre.nom,
      famille: genre.famille,
    });

    this.famillesSharedCollection = this.familleService.addFamilleToCollectionIfMissing(this.famillesSharedCollection, genre.famille);
  }

  protected loadRelationshipsOptions(): void {
    this.familleService
      .query()
      .pipe(map((res: HttpResponse<IFamille[]>) => res.body ?? []))
      .pipe(
        map((familles: IFamille[]) => this.familleService.addFamilleToCollectionIfMissing(familles, this.editForm.get('famille')!.value))
      )
      .subscribe((familles: IFamille[]) => (this.famillesSharedCollection = familles));
  }

  protected createFromForm(): IGenre {
    return {
      ...new Genre(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      famille: this.editForm.get(['famille'])!.value,
    };
  }
}
