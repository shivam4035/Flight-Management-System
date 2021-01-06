import { TestBed } from '@angular/core/testing';

import { FlightRegistrationService } from './flight-registration.service';

describe('FlightRegistrationService', () => {
  let service: FlightRegistrationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlightRegistrationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
