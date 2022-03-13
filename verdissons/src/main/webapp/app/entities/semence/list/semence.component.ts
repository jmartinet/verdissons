import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISemence } from '../semence.model';
import { SemenceService } from '../service/semence.service';
import { SemenceDeleteDialogComponent } from '../delete/semence-delete-dialog.component';

@Component({
  selector: 'jhi-semence',
  templateUrl: './semence.component.html',
})
export class SemenceComponent implements OnInit {
  semences?: ISemence[];
  isLoading = false;

  constructor(protected semenceService: SemenceService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.semenceService.query().subscribe({
      next: (res: HttpResponse<ISemence[]>) => {
        this.isLoading = false;
        this.semences = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ISemence): number {
    return item.id!;
  }

  delete(semence: ISemence): void {
    const modalRef = this.modalService.open(SemenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.semence = semence;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
