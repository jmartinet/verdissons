import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SemenceService } from '../service/semence.service';
import { ISemence, Semence } from '../semence.model';
import { IVariete } from 'app/entities/variete/variete.model';
import { VarieteService } from 'app/entities/variete/service/variete.service';

import { SemenceUpdateComponent } from './semence-update.component';

describe('Semence Management Update Component', () => {
  let comp: SemenceUpdateComponent;
  let fixture: ComponentFixture<SemenceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let semenceService: SemenceService;
  let varieteService: VarieteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SemenceUpdateComponent],
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
      .overrideTemplate(SemenceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SemenceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    semenceService = TestBed.inject(SemenceService);
    varieteService = TestBed.inject(VarieteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Variete query and add missing value', () => {
      const semence: ISemence = { id: 456 };
      const variete: IVariete = { id: 73055 };
      semence.variete = variete;

      const varieteCollection: IVariete[] = [{ id: 85355 }];
      jest.spyOn(varieteService, 'query').mockReturnValue(of(new HttpResponse({ body: varieteCollection })));
      const additionalVarietes = [variete];
      const expectedCollection: IVariete[] = [...additionalVarietes, ...varieteCollection];
      jest.spyOn(varieteService, 'addVarieteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ semence });
      comp.ngOnInit();

      expect(varieteService.query).toHaveBeenCalled();
      expect(varieteService.addVarieteToCollectionIfMissing).toHaveBeenCalledWith(varieteCollection, ...additionalVarietes);
      expect(comp.varietesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const semence: ISemence = { id: 456 };
      const variete: IVariete = { id: 12260 };
      semence.variete = variete;

      activatedRoute.data = of({ semence });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(semence));
      expect(comp.varietesSharedCollection).toContain(variete);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Semence>>();
      const semence = { id: 123 };
      jest.spyOn(semenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ semence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: semence }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(semenceService.update).toHaveBeenCalledWith(semence);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Semence>>();
      const semence = new Semence();
      jest.spyOn(semenceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ semence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: semence }));
      saveSubject.complete();

      // THEN
      expect(semenceService.create).toHaveBeenCalledWith(semence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Semence>>();
      const semence = { id: 123 };
      jest.spyOn(semenceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ semence });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(semenceService.update).toHaveBeenCalledWith(semence);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackVarieteById', () => {
      it('Should return tracked Variete primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackVarieteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
