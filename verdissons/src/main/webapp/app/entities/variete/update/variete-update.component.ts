import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVariete, Variete } from '../variete.model';
import { VarieteService } from '../service/variete.service';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';
import { BotanicItemService } from 'app/entities/botanicItem/service/botanicItem.service';

@Component({
	selector: 'jhi-variete-update',
	templateUrl: './variete-update.component.html',
})
export class VarieteUpdateComponent implements OnInit {

	isSaving = false;

	especesSharedCollection: IBotanicItem[] = [];

	editForm =  this.fb.group({
		id: [],
		nom: [],
		conseilCulture: [],
		culture: [],
		exposition: [],
		besoinEau: [],
		natureSol: [],
		qualiteSol: [],
		espece: this.fb.group({
			id: [],
			nom: [],
			parent: [],
			type: [],
		}),
	});

	constructor(
		protected varieteService: VarieteService,
		protected especeService: BotanicItemService,
		protected activatedRoute: ActivatedRoute,
		protected fb: FormBuilder
	) { }

	ngOnInit(): void {
		this.activatedRoute.data.subscribe(({ variete }) => {
			this.updateForm(variete);

			this.loadRelationshipsOptions();
		});
		this.editForm.controls.espece.valueChanges.subscribe((data) => {
			data;
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

	trackEspeceById(index: number, item: IBotanicItem): number {
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
			nom: variete.nom,
			conseilCulture: variete.conseilCulture,
			culture: variete.culture,
			exposition: variete.exposition,
			besoinEau: variete.besoinEau,
			natureSol: variete.natureSol,
			qualiteSol: variete.qualiteSol,
			espece: variete.espece,
		});

		this.especesSharedCollection = this.especeService.addBotanicItemToCollectionIfMissing(this.especesSharedCollection, variete.espece);
	}

	protected loadRelationshipsOptions(): void {
		this.especeService
			.query()
			.pipe(map((res: HttpResponse<IBotanicItem[]>) => res.body ?? []))
			.pipe(map((especes: IBotanicItem[]) => this.especeService.addBotanicItemToCollectionIfMissing(especes, this.editForm.get('espece')!.value)))
			.subscribe((especes: IBotanicItem[]) => (this.especesSharedCollection = especes));
	}

	protected createFromForm(): IVariete {
		return {
			...new Variete(),
			id: this.editForm.get(['id'])!.value,
			nom: this.editForm.get(['nom'])!.value,
			conseilCulture: this.editForm.get(['conseilCulture'])!.value,
			culture: this.editForm.get(['culture'])!.value,
			exposition: this.editForm.get(['exposition'])!.value,
			besoinEau: this.editForm.get(['besoinEau'])!.value,
			natureSol: this.editForm.get(['natureSol'])!.value,
			qualiteSol: this.editForm.get(['qualiteSol'])!.value,
			espece: this.editForm.get(['espece'])!.value,
		};
	}
}
