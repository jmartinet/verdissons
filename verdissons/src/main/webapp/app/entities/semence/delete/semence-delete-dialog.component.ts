import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISemence } from '../semence.model';
import { SemenceService } from '../service/semence.service';

@Component({
  templateUrl: './semence-delete-dialog.component.html',
})
export class SemenceDeleteDialogComponent {
  semence?: ISemence;

  constructor(protected semenceService: SemenceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.semenceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
