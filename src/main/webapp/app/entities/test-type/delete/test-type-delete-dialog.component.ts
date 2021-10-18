import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITestType } from '../test-type.model';
import { TestTypeService } from '../service/test-type.service';

@Component({
  templateUrl: './test-type-delete-dialog.component.html',
})
export class TestTypeDeleteDialogComponent {
  testType?: ITestType;

  constructor(protected testTypeService: TestTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.testTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
