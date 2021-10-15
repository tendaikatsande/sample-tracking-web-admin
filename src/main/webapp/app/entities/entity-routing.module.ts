import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'sample',
        data: { pageTitle: 'sampleTrackingApp.sample.home.title' },
        loadChildren: () => import('./sample/sample.module').then(m => m.SampleModule),
      },
      {
        path: 'shipment',
        data: { pageTitle: 'sampleTrackingApp.shipment.home.title' },
        loadChildren: () => import('./shipment/shipment.module').then(m => m.ShipmentModule),
      },
      {
        path: 'patient',
        data: { pageTitle: 'sampleTrackingApp.patient.home.title' },
        loadChildren: () => import('./patient/patient.module').then(m => m.PatientModule),
      },
      {
        path: 'laboratory',
        data: { pageTitle: 'sampleTrackingApp.laboratory.home.title' },
        loadChildren: () => import('./laboratory/laboratory.module').then(m => m.LaboratoryModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'sampleTrackingApp.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'sample-type',
        data: { pageTitle: 'sampleTrackingApp.sampleType.home.title' },
        loadChildren: () => import('./sample-type/sample-type.module').then(m => m.SampleTypeModule),
      },
      {
        path: 'test-type',
        data: { pageTitle: 'sampleTrackingApp.testType.home.title' },
        loadChildren: () => import('./test-type/test-type.module').then(m => m.TestTypeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
