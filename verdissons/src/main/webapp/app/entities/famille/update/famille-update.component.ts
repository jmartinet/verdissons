import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFamille, Famille } from '../famille.model';
import { FamilleService } from '../service/famille.service';

@Component({
  selector: 'jhi-famille-update',
  templateUrl: './famille-update.component.html',
})
export class FamilleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [],
  });

  constructor(protected familleService: FamilleService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ famille }) => {
      this.updateForm(famille);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const famille = this.createFromForm();
    if (famille.id !== undefined) {
      this.subscribeToSaveResponse(this.familleService.update(famille));
    } else {
      this.subscribeToSaveResponse(this.familleService.create(famille));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamille>>): void {
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

  protected updateForm(famille: IFamille): void {
    this.editForm.patchValue({
      id: famille.id,
      nom: famille.nom,
    });
  }

  protected createFromForm(): IFamille {
    return {
      ...new Famille(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
    };
  }
}
