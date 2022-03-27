import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';
import { BotanicItemService } from 'app/entities/botanicItem/service/botanicItem.service';


@Component({
  templateUrl: './famille-delete-dialog.component.html',
})
export class FamilleDeleteDialogComponent {
  famille?: IBotanicItem;

  constructor(protected familleService: BotanicItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.familleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
