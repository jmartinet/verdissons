import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FamilleService } from '../service/famille.service';
import { IFamille, Famille } from '../famille.model';

import { FamilleUpdateComponent } from './famille-update.component';

describe('Famille Management Update Component', () => {
  let comp: FamilleUpdateComponent;
  let fixture: ComponentFixture<FamilleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let familleService: FamilleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FamilleUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FamilleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FamilleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    familleService = TestBed.inject(FamilleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const famille: IFamille = { id: 456 };

      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(famille));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Famille>>();
      const famille = { id: 123 };
      jest.spyOn(familleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: famille }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(familleService.update).toHaveBeenCalledWith(famille);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Famille>>();
      const famille = new Famille();
      jest.spyOn(familleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: famille }));
      saveSubject.complete();

      // THEN
      expect(familleService.create).toHaveBeenCalledWith(famille);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Famille>>();
      const famille = { id: 123 };
      jest.spyOn(familleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ famille });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(familleService.update).toHaveBeenCalledWith(famille);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
