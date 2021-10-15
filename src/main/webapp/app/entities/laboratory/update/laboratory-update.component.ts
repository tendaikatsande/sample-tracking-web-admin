import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ILaboratory, Laboratory } from '../laboratory.model';
import { LaboratoryService } from '../service/laboratory.service';

@Component({
  selector: 'jhi-laboratory-update',
  templateUrl: './laboratory-update.component.html',
})
export class LaboratoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    type: [],
    code: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
  });

  constructor(protected laboratoryService: LaboratoryService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ laboratory }) => {
      if (laboratory.id === undefined) {
        const today = dayjs().startOf('day');
        laboratory.createdDate = today;
        laboratory.lastModifiedDate = today;
      }

      this.updateForm(laboratory);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const laboratory = this.createFromForm();
    if (laboratory.id !== undefined) {
      this.subscribeToSaveResponse(this.laboratoryService.update(laboratory));
    } else {
      this.subscribeToSaveResponse(this.laboratoryService.create(laboratory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILaboratory>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
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

  protected updateForm(laboratory: ILaboratory): void {
    this.editForm.patchValue({
      id: laboratory.id,
      name: laboratory.name,
      type: laboratory.type,
      code: laboratory.code,
      createdBy: laboratory.createdBy,
      createdDate: laboratory.createdDate ? laboratory.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: laboratory.lastModifiedBy,
      lastModifiedDate: laboratory.lastModifiedDate ? laboratory.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ILaboratory {
    return {
      ...new Laboratory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      code: this.editForm.get(['code'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
