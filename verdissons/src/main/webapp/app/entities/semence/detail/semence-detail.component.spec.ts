import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SemenceDetailComponent } from './semence-detail.component';

describe('Semence Management Detail Component', () => {
  let comp: SemenceDetailComponent;
  let fixture: ComponentFixture<SemenceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SemenceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ semence: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SemenceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SemenceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load semence on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.semence).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
