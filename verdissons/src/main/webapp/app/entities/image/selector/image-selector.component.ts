import { Component, Input } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ImageCropperDialogComponent } from '../cropper/image-cropper-dialog.component';

@Component({
	selector: 'jhi-image-selector',
	styleUrls: ['./image-selector.component.scss'],
	templateUrl: './image-selector.component.html',
})
export class ImageSelectorComponent {
	
	@Input() 
    croppedImage: any = '';

	constructor(
		protected modalService: NgbModal
	) { }

	fileChangeEvent(event: any): void {
		//this.imageChangedEvent = event;
		const modalRef = this.modalService.open(ImageCropperDialogComponent, { size: 'lg', backdrop: 'static' });
		modalRef.componentInstance.imageFile = event.target.files[0];
		// unsubscribe not needed because closed completes on modal close
		modalRef.closed.subscribe(reason => {
			if (reason === 'cropped') {
				this.croppedImage = modalRef.componentInstance.croppedImage;
			}
		});
	}

}
