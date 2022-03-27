import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { Genre } from '../genre.model';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';
import { BotanicItemService } from 'app/entities/botanicItem/service/botanicItem.service';

@Component({
  selector: 'jhi-genre-update',
  templateUrl: './genre-update.component.html',
})
export class GenreUpdateComponent implements OnInit {
  isSaving = false;

  famillesSharedCollection: IBotanicItem[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    famille: [],
  });

  constructor(
    protected botanicItemService: BotanicItemService,
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
      this.subscribeToSaveResponse(this.botanicItemService.update(genre));
    } else {
      this.subscribeToSaveResponse(this.botanicItemService.create(genre));
    }
  }

  trackFamilleById(index: number, item: IBotanicItem): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBotanicItem>>): void {
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

  protected updateForm(genre: IBotanicItem): void {
    this.editForm.patchValue({
      id: genre.id,
      nom: genre.nom,
      famille: genre.parent,
    });

    this.famillesSharedCollection = this.botanicItemService.addBotanicItemToCollectionIfMissing(this.famillesSharedCollection, genre.parent);
  }

  protected loadRelationshipsOptions(): void {
    this.botanicItemService
      .query({'type.equals': 'FAMILLE'})
      .pipe(map((res: HttpResponse<IBotanicItem[]>) => res.body ?? []))
      .pipe(
        map((familles: IBotanicItem[]) => this.botanicItemService.addBotanicItemToCollectionIfMissing(familles, this.editForm.get('famille')!.value))
      )
      .subscribe((familles: IBotanicItem[]) => (this.famillesSharedCollection = familles));
  }

  protected createFromForm(): IBotanicItem {
    return {
      ...new Genre(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      parent: this.editForm.get(['famille'])!.value,
    };
  }
}
