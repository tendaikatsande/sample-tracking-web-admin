import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITestType } from '../test-type.model';

@Component({
  selector: 'jhi-test-type-detail',
  templateUrl: './test-type-detail.component.html',
})
export class TestTypeDetailComponent implements OnInit {
  testType: ITestType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ testType }) => {
      this.testType = testType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
