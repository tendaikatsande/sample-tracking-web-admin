import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISample } from '../sample.model';
import { SampleService } from '../service/sample.service';

@Component({
  templateUrl: './sample-delete-dialog.component.html',
})
export class SampleDeleteDialogComponent {
  sample?: ISample;

  constructor(protected sampleService: SampleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.sampleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
