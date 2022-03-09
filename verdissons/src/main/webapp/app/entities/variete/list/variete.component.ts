import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVariete } from '../variete.model';
import { VarieteService } from '../service/variete.service';
import { VarieteDeleteDialogComponent } from '../delete/variete-delete-dialog.component';

@Component({
  selector: 'jhi-variete',
  templateUrl: './variete.component.html',
})
export class VarieteComponent implements OnInit {
  varietes?: IVariete[];
  isLoading = false;

  constructor(protected varieteService: VarieteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.varieteService.query().subscribe({
      next: (res: HttpResponse<IVariete[]>) => {
        this.isLoading = false;
        this.varietes = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVariete): number {
    return item.id!;
  }

  delete(variete: IVariete): void {
    const modalRef = this.modalService.open(VarieteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.variete = variete;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
