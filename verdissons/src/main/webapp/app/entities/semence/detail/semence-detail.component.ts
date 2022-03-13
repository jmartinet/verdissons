import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISemence } from '../semence.model';

@Component({
  selector: 'jhi-semence-detail',
  templateUrl: './semence-detail.component.html',
})
export class SemenceDetailComponent implements OnInit {
  semence: ISemence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ semence }) => {
      this.semence = semence;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
