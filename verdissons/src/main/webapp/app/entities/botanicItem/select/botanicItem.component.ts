import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, FormGroupDirective, NG_VALUE_ACCESSOR } from '@angular/forms';

import { IBotanicItem } from '../botanicItem.model';

import { BotanicItemService } from '../service/botanicItem.service';

@Component({
	selector: 'jhi-botanic-item',
	templateUrl: './botanicItem.component.html',
	styleUrls: ['./botanicItem.component.scss'],
	encapsulation: ViewEncapsulation.None,
	providers: [
		{
			provide: NG_VALUE_ACCESSOR,
			useExisting: BotanicItemComponent,
			multi: true
		}
	]
})
export class BotanicItemComponent implements OnInit {

	espece: IBotanicItem | undefined;
	
	@Input()
    formGroupName!: string;
    
  	editForm!: FormGroup;

	botanicItems?: IBotanicItem[];
	isLoading = false;
	
	searchValue: string | undefined;

	itemsSharedCollection: IBotanicItem[] = [];

	constructor(
		protected botanicItemService: BotanicItemService,
		protected fb: FormBuilder,
		private rootFormGroup: FormGroupDirective
	) { }

	ngOnInit(): void {
    	this.editForm = this.rootFormGroup.control.get(this.formGroupName) as FormGroup;
    	const espece = this.editForm.value;
		this.itemsSharedCollection = [];
		this.itemsSharedCollection.push(espece);
	}

	trackId(index: number, item: IBotanicItem): number {
		return item.id!;
	}

	search(event: any): void {
		const val = event.target.value
		if (val.length >= 3) {
			this.searchItems(val);
		}
	}

	getFamilleNom(item: IBotanicItem): string {
		const nom = item.type === "FAMILLE" ? item.nom : item.type === "GENRE" ? item.parent?.nom : item.parent?.parent?.nom;
		return this.buildLibelle(nom);
	}

	getGenreNom(item: IBotanicItem): string {
		const nom = item.type === "GENRE" ? item.nom : item.type === "ESPECE" ? item.parent?.nom : "";
		return this.buildLibelle(nom);
	}

	getEspeceNom(item: IBotanicItem): string {
		const nom = item.type === "ESPECE" ? item.nom : "";
		return this.buildLibelle(nom);
	}

	selectFamille(item: IBotanicItem): void {
		const famille = item.type === "FAMILLE" ? item : item.type === "GENRE" ? item.parent : item.type === "ESPECE" ? item.parent?.parent : undefined;
		if (famille) {
			this.botanicItemService.query({ 'parent.equals': famille.id })
				.subscribe({
					next: (res: HttpResponse<IBotanicItem[]>) => {
						this.isLoading = false;
						this.onSuccess(res.body);
					},
					error: () => {
						this.isLoading = false;
						this.onError();
					},
				});
			this.clearForm();
		}
	}

	selectGenre(item: IBotanicItem): void {
		const genre = item.type === "GENRE" ? item : item.type === "ESPECE" ? item.parent : undefined;
		if (genre) {
			this.botanicItemService.query({ 'parent.equals': genre.id })
				.subscribe({
					next: (res: HttpResponse<IBotanicItem[]>) => {
						this.isLoading = false;
						this.onSuccess(res.body);
					},
					error: () => {
						this.isLoading = false;
						this.onError();
					},
				});
			this.clearForm();
		}
	}

	selectEspece(item: IBotanicItem): void {
		const espece = item.type === "ESPECE" ? item : undefined;
		if (espece) {
			this.espece = espece;
			this.itemsSharedCollection = [];
			this.itemsSharedCollection.push(this.espece);
			this.editForm.setValue(espece);
			this.clearForm();
		}
	}

	protected buildLibelle(nom: string | null | undefined): string {
		if (this.searchValue && this.searchValue.length >= 3) {
			const tab: Array<string> | undefined = nom?.split(this.searchValue.toUpperCase());
			const fromatted = tab?.join("<mark class=\"mark-botanic\">" + this.searchValue.toUpperCase() + "</mark>");
			return fromatted ? fromatted : "";
		} else {
			return nom ? nom : "";
		}
	}

	protected searchItems(val: string): void {
		this.botanicItemService.query({ 'libelle.contains': val })
			.subscribe({
				next: (res: HttpResponse<IBotanicItem[]>) => {
					this.isLoading = false;
					this.onSuccess(res.body);
				},
				error: () => {
					this.isLoading = false;
					this.onError();
				},
			});
	}

	protected onSuccess(data: IBotanicItem[] | null): void {
		this.itemsSharedCollection = data ?? [];
		this.itemsSharedCollection = this.itemsSharedCollection.filter(elm => {
			if (elm.type === "FAMILLE") {
				return this.itemsSharedCollection.filter(bi => bi.type === "GENRE" && bi.parent).map(bi => bi.parent?.id).indexOf(elm.id) === -1;
			}
			if (elm.type === "GENRE") {
				return this.itemsSharedCollection.filter(bi => bi.type === "ESPECE" && bi.parent).map(bi => bi.parent?.id).indexOf(elm.id) === -1;
			}
			return true;
		});
		this.itemsSharedCollection = this.itemsSharedCollection.sort((it1, it2) => {
			const fam1 = it1.type === "FAMILLE" ? it1 : it1.type === "GENRE" ? it1.parent : it1.parent?.parent
			const fam2 = it2.type === "FAMILLE" ? it2 : it2.type === "GENRE" ? it2.parent : it2.parent?.parent
			if (fam1 && fam2 && fam1.nom && fam2.nom) {
				if (fam1.nom > fam2.nom) {
					return 1;
				}

				if (fam1.nom < fam2.nom) {
					return -1;
				}
				const ge1 = it1.type === "GENRE" ? it1 : it1.type === "ESPECE" ? it1.parent : undefined;
				const ge2 = it2.type === "GENRE" ? it2 : it2.type === "ESPECE" ? it2.parent : undefined;
				if (ge1 && ge2 && ge1.nom && ge2.nom) {
					if (ge1.nom > ge2.nom) {
						return 1;
					}

					if (ge1.nom < ge2.nom) {
						return -1;
					}
					const es1 = it1.type === "ESPECE" ? it1 : undefined;
					const es2 = it2.type === "ESPECE" ? it2 : undefined;
					if (es1 && es2 && es1.nom && es2.nom) {
						if (es1.nom > es2.nom) {
							return 1;
						}

						if (es1.nom < es2.nom) {
							return -1;
						}
						return 0;
					}
				}
			}

			return 0;
		});
	}

	protected onError(): void {
		this.botanicItems = [];
	}

	protected clearForm(): void {
		this.searchValue = "";
	}
}
