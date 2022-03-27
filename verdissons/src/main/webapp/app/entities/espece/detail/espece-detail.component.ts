import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IBotanicItem } from 'app/entities/botanicItem/botanicItem.model';

@Component({
  selector: 'jhi-espece-detail',
  templateUrl: './espece-detail.component.html',
})
export class EspeceDetailComponent implements OnInit {
  espece: IBotanicItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ espece }) => {
      this.espece = espece;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
