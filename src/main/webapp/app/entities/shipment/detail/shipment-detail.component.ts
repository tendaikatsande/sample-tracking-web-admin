import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShipment } from '../shipment.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-shipment-detail',
  templateUrl: './shipment-detail.component.html',
})
export class ShipmentDetailComponent implements OnInit {
  shipment: IShipment | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipment }) => {
      this.shipment = shipment;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
