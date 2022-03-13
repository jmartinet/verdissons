import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEspece, Espece } from '../espece.model';
import { EspeceService } from '../service/espece.service';

@Component({
  selector: 'jhi-espece-update',
  templateUrl: './espece-update.component.html',
})
export class EspeceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected especeService: EspeceService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ espece }) => {
      this.updateForm(espece);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const espece = this.createFromForm();
    if (espece.id !== undefined) {
      this.subscribeToSaveResponse(this.especeService.update(espece));
    } else {
      this.subscribeToSaveResponse(this.especeService.create(espece));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEspece>>): void {
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

  protected updateForm(espece: IEspece): void {
    this.editForm.patchValue({
      id: espece.id,
    });
  }

  protected createFromForm(): IEspece {
    return {
      ...new Espece(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
