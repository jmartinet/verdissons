import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISemence, Semence } from '../semence.model';
import { SemenceService } from '../service/semence.service';
import { IVariete } from 'app/entities/variete/variete.model';
import { VarieteService } from 'app/entities/variete/service/variete.service';

@Component({
  selector: 'jhi-semence-update',
  templateUrl: './semence-update.component.html',
})
export class SemenceUpdateComponent implements OnInit {
  isSaving = false;

  varietesSharedCollection: IVariete[] = [];

  editForm = this.fb.group({
    id: [],
    typeSemis: [],
    conseilSemis: [],
    periodeSemisDebut: [],
    periodeSemisFin: [],
    variete: [],
  });

  constructor(
    protected semenceService: SemenceService,
    protected varieteService: VarieteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ semence }) => {
      this.updateForm(semence);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const semence = this.createFromForm();
    if (semence.id !== undefined) {
      this.subscribeToSaveResponse(this.semenceService.update(semence));
    } else {
      this.subscribeToSaveResponse(this.semenceService.create(semence));
    }
  }

  trackVarieteById(index: number, item: IVariete): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISemence>>): void {
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

  protected updateForm(semence: ISemence): void {
    this.editForm.patchValue({
      id: semence.id,
      typeSemis: semence.typeSemis,
      conseilSemis: semence.conseilSemis,
      periodeSemisDebut: semence.periodeSemisDebut,
      periodeSemisFin: semence.periodeSemisFin,
      variete: semence.variete,
    });

    this.varietesSharedCollection = this.varieteService.addVarieteToCollectionIfMissing(this.varietesSharedCollection, semence.variete);
  }

  protected loadRelationshipsOptions(): void {
    this.varieteService
      .query()
      .pipe(map((res: HttpResponse<IVariete[]>) => res.body ?? []))
      .pipe(
        map((varietes: IVariete[]) => this.varieteService.addVarieteToCollectionIfMissing(varietes, this.editForm.get('variete')!.value))
      )
      .subscribe((varietes: IVariete[]) => (this.varietesSharedCollection = varietes));
  }

  protected createFromForm(): ISemence {
    return {
      ...new Semence(),
      id: this.editForm.get(['id'])!.value,
      typeSemis: this.editForm.get(['typeSemis'])!.value,
      conseilSemis: this.editForm.get(['conseilSemis'])!.value,
      periodeSemisDebut: this.editForm.get(['periodeSemisDebut'])!.value,
      periodeSemisFin: this.editForm.get(['periodeSemisFin'])!.value,
      variete: this.editForm.get(['variete'])!.value,
    };
  }
}
