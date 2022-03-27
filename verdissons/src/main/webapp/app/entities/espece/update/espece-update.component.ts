import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { Espece } from '../espece.model';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';
import { BotanicItemService } from 'app/entities/botanicItem/service/botanicItem.service';

@Component({
	selector: 'jhi-espece-update',
	templateUrl: './espece-update.component.html',
})
export class EspeceUpdateComponent implements OnInit {
	isSaving = false;

	famillesSharedCollection: IBotanicItem[] = [];

	genresSharedCollection: IBotanicItem[] = [];

	editForm = this.fb.group({
		id: [],
		nom: [],
		genre: [],
		famille: [],
	});

	constructor(
		protected botanicService: BotanicItemService,
		protected activatedRoute: ActivatedRoute,
		protected fb: FormBuilder
	) { }

	ngOnInit(): void {
		this.activatedRoute.data.subscribe(({ espece }) => {
			this.updateForm(espece);

			this.loadRelationshipsOptions();
			this.selectFamille(espece.parent?.parent);
		});


		this.editForm.get("famille")?.valueChanges
			.subscribe(f => {
				this.genresSharedCollection = [];
				this.selectFamille(f);
			});
	}

	previousState(): void {
		window.history.back();
	}

	save(): void {
		this.isSaving = true;
		const espece = this.createFromForm();
		if (espece.id !== undefined) {
			this.subscribeToSaveResponse(this.botanicService.update(espece));
		} else {
			this.subscribeToSaveResponse(this.botanicService.create(espece));
		}
	}

	trackFamilleById(index: number, item: IBotanicItem): number {
		return item.id!;
	}

	trackGenreById(index: number, item: IBotanicItem): number {
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

	protected updateForm(espece: IBotanicItem): void {
		this.editForm.patchValue({
			id: espece.id,
			nom: espece.nom,
			genre: espece.parent,
			famille: espece.parent?.parent,
		});

		this.genresSharedCollection = this.botanicService.addBotanicItemToCollectionIfMissing(this.genresSharedCollection, espece.parent);
		this.famillesSharedCollection = this.botanicService.addBotanicItemToCollectionIfMissing(this.famillesSharedCollection, espece.parent?.parent);
	}

	protected selectFamille(f: IBotanicItem): void {
		this.botanicService
			.query({'parent.equals':f.id})
			.pipe(map((res: HttpResponse<IBotanicItem[]>) => res.body ?? []))
			.pipe(
				map((genres: IBotanicItem[]) => this.botanicService.addBotanicItemToCollectionIfMissing(genres, this.editForm.get('genre')!.value))
			)
			.subscribe((genres: IBotanicItem[]) => (this.genresSharedCollection = genres));
	}

	protected loadRelationshipsOptions(): void {
		this.botanicService
			.query({'type.equals':'FAMILLE'})
			.pipe(map((res: HttpResponse<IBotanicItem[]>) => res.body ?? []))
			.pipe(
				map((familles: IBotanicItem[]) => this.botanicService.addBotanicItemToCollectionIfMissing(familles, this.editForm.get('famille')!.value))
			)
			.subscribe((familles: IBotanicItem[]) => (this.famillesSharedCollection = familles));
	}

	protected createFromForm(): IBotanicItem {
		return {
			...new Espece(),
			id: this.editForm.get(['id'])!.value,
			nom: this.editForm.get(['nom'])!.value,
			parent: this.editForm.get(['genre'])!.value,
		};
	}
}
