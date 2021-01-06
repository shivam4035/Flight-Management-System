import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightFareAddComponent } from './flight-fare-add.component';

describe('FlightFareAddComponent', () => {
  let component: FlightFareAddComponent;
  let fixture: ComponentFixture<FlightFareAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightFareAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightFareAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
