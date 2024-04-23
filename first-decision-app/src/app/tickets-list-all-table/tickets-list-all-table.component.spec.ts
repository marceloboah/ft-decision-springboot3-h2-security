import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsListAllTableComponent } from './tickets-list-all-table.component';

describe('TicketsListAllTableComponent', () => {
  let component: TicketsListAllTableComponent;
  let fixture: ComponentFixture<TicketsListAllTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TicketsListAllTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TicketsListAllTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
