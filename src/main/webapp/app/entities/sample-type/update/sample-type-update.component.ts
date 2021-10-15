import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ISampleType, SampleType } from '../sample-type.model';
import { SampleTypeService } from '../service/sample-type.service';

@Component({
  selector: 'jhi-sample-type-update',
  templateUrl: './sample-type-update.component.html',
})
export class SampleTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    prefix: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
  });

  constructor(protected sampleTypeService: SampleTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sampleType }) => {
      if (sampleType.id === undefined) {
        const today = dayjs().startOf('day');
        sampleType.createdDate = today;
        sampleType.lastModifiedDate = today;
      }

      this.updateForm(sampleType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sampleType = this.createFromForm();
    if (sampleType.id !== undefined) {
      this.subscribeToSaveResponse(this.sampleTypeService.update(sampleType));
    } else {
      this.subscribeToSaveResponse(this.sampleTypeService.create(sampleType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISampleType>>): void {
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

  protected updateForm(sampleType: ISampleType): void {
    this.editForm.patchValue({
      id: sampleType.id,
      name: sampleType.name,
      prefix: sampleType.prefix,
      createdBy: sampleType.createdBy,
      createdDate: sampleType.createdDate ? sampleType.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: sampleType.lastModifiedBy,
      lastModifiedDate: sampleType.lastModifiedDate ? sampleType.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ISampleType {
    return {
      ...new SampleType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      prefix: this.editForm.get(['prefix'])!.value,
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
