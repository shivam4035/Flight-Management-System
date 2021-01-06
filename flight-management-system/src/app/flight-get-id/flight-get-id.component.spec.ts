import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightGetIdComponent } from './flight-get-id.component';

describe('FlightGetIdComponent', () => {
  let component: FlightGetIdComponent;
  let fixture: ComponentFixture<FlightGetIdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightGetIdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightGetIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
