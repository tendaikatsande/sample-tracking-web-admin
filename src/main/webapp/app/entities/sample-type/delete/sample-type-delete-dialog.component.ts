import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISampleType } from '../sample-type.model';
import { SampleTypeService } from '../service/sample-type.service';

@Component({
  templateUrl: './sample-type-delete-dialog.component.html',
})
export class SampleTypeDeleteDialogComponent {
  sampleType?: ISampleType;

  constructor(protected sampleTypeService: SampleTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.sampleTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
