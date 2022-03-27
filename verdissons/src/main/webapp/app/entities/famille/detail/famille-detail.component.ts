import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';

@Component({
  selector: 'jhi-famille-detail',
  templateUrl: './famille-detail.component.html',
})
export class FamilleDetailComponent implements OnInit {
  famille: IBotanicItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ famille }) => {
      this.famille = famille;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
