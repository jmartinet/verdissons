import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VarieteService } from '../service/variete.service';

import { IVariete } from '../variete.model';

@Component({
	selector: 'jhi-variete-detail',
	templateUrl: './variete-detail.component.html',
})
export class VarieteDetailComponent implements OnInit {
	variete: IVariete | null = null;

	imageToShow: any;
	isImageLoading = false;

	constructor(protected activatedRoute: ActivatedRoute, protected varieteService: VarieteService) { }

	ngOnInit(): void {
		this.activatedRoute.data.subscribe(({ variete }) => {
			this.variete = variete;
			if (this.variete?.image) {
				this.getImageFromService(this.variete.image);
			}
		});
	}

	getImageFromService(image: string): void {
		this.isImageLoading = true;
		this.varieteService.image(image).subscribe(data => {
			if (data.body) {
				this.createImageFromBlob(data.body);
			}
			this.isImageLoading = false;
		}, error => {
			this.isImageLoading = false;
		});
	}

	createImageFromBlob(image: Blob): void {
		const reader = new FileReader();
		reader.addEventListener("load", () => {
			this.imageToShow = reader.result;
		}, false);

		reader.readAsDataURL(image);
	}

	previousState(): void {
		window.history.back();
	}
}
