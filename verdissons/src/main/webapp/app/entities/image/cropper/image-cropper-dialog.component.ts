import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ImageCroppedEvent } from 'ngx-image-cropper';


@Component({
	templateUrl: './image-cropper-dialog.component.html',
})
export class ImageCropperDialogComponent {

	imageFile?: File;
	croppedImage: any = '';
	imageChangedEvent: any = '';

	tempImage: any = '';

	constructor(protected activeModal: NgbActiveModal) { }

	cancel(): void {
		this.activeModal.dismiss();
	}

	confirmCrop(): void {
		this.croppedImage = this.tempImage;
		this.activeModal.close('cropped');
	}

	imageCropped(event: ImageCroppedEvent): void {
		this.tempImage = event.base64;
	}
	imageLoaded(): void {
		// show message
	}
	cropperReady(): void {
		// cropper ready
	}
	loadImageFailed(): void {
		// show message
	}
}
