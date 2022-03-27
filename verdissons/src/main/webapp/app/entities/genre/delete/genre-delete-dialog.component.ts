import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';
import { BotanicItemService } from 'app/entities/botanicItem/service/botanicItem.service';

@Component({
  templateUrl: './genre-delete-dialog.component.html',
})
export class GenreDeleteDialogComponent {
  genre?: IBotanicItem;

  constructor(protected genreService: BotanicItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.genreService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
