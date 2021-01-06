import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IATADialogComponent } from './iata-dialog.component';

describe('IATADialogComponent', () => {
  let component: IATADialogComponent;
  let fixture: ComponentFixture<IATADialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IATADialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IATADialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
