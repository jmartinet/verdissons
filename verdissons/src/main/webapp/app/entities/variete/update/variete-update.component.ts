import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVariete, Variete } from '../variete.model';
import { VarieteService } from '../service/variete.service';
import { IGenre } from 'app/entities/genre/genre.model';
import { GenreService } from 'app/entities/genre/service/genre.service';

@Component({
  selector: 'jhi-variete-update',
  templateUrl: './variete-update.component.html',
})
export class VarieteUpdateComponent implements OnInit {
  isSaving = false;

  genresSharedCollection: IGenre[] = [];

  editForm = this.fb.group({
    id: [],
    nomLatin: [],
    conseilCulture: [],
    culture: [],
    exposition: [],
    besoinEau: [],
    natureSol: [],
    qualiteSol: [],
    genre: [],
  });

  constructor(
    protected varieteService: VarieteService,
    protected genreService: GenreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ variete }) => {
      this.updateForm(variete);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const variete = this.createFromForm();
    if (variete.id !== undefined) {
      this.subscribeToSaveResponse(this.varieteService.update(variete));
    } else {
      this.subscribeToSaveResponse(this.varieteService.create(variete));
    }
  }

  trackGenreById(index: number, item: IGenre): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVariete>>): void {
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

  protected updateForm(variete: IVariete): void {
    this.editForm.patchValue({
      id: variete.id,
      nomLatin: variete.nomLatin,
      conseilCulture: variete.conseilCulture,
      culture: variete.culture,
      exposition: variete.exposition,
      besoinEau: variete.besoinEau,
      natureSol: variete.natureSol,
      qualiteSol: variete.qualiteSol,
      genre: variete.genre,
    });

    this.genresSharedCollection = this.genreService.addGenreToCollectionIfMissing(this.genresSharedCollection, variete.genre);
  }

  protected loadRelationshipsOptions(): void {
    this.genreService
      .query()
      .pipe(map((res: HttpResponse<IGenre[]>) => res.body ?? []))
      .pipe(map((genres: IGenre[]) => this.genreService.addGenreToCollectionIfMissing(genres, this.editForm.get('genre')!.value)))
      .subscribe((genres: IGenre[]) => (this.genresSharedCollection = genres));
  }

  protected createFromForm(): IVariete {
    return {
      ...new Variete(),
      id: this.editForm.get(['id'])!.value,
      nomLatin: this.editForm.get(['nomLatin'])!.value,
      conseilCulture: this.editForm.get(['conseilCulture'])!.value,
      culture: this.editForm.get(['culture'])!.value,
      exposition: this.editForm.get(['exposition'])!.value,
      besoinEau: this.editForm.get(['besoinEau'])!.value,
      natureSol: this.editForm.get(['natureSol'])!.value,
      qualiteSol: this.editForm.get(['qualiteSol'])!.value,
      genre: this.editForm.get(['genre'])!.value,
    };
  }
}
