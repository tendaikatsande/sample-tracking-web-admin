import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITestType, TestType } from '../test-type.model';
import { TestTypeService } from '../service/test-type.service';

@Component({
  selector: 'jhi-test-type-update',
  templateUrl: './test-type-update.component.html',
})
export class TestTypeUpdateComponent implements OnInit {
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

  constructor(protected testTypeService: TestTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ testType }) => {
      if (testType.id === undefined) {
        const today = dayjs().startOf('day');
        testType.createdDate = today;
        testType.lastModifiedDate = today;
      }

      this.updateForm(testType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const testType = this.createFromForm();
    if (testType.id !== undefined) {
      this.subscribeToSaveResponse(this.testTypeService.update(testType));
    } else {
      this.subscribeToSaveResponse(this.testTypeService.create(testType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestType>>): void {
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

  protected updateForm(testType: ITestType): void {
    this.editForm.patchValue({
      id: testType.id,
      name: testType.name,
      prefix: testType.prefix,
      createdBy: testType.createdBy,
      createdDate: testType.createdDate ? testType.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: testType.lastModifiedBy,
      lastModifiedDate: testType.lastModifiedDate ? testType.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ITestType {
    return {
      ...new TestType(),
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
