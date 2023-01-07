import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardTaskListComponent } from './dashboard-task-list.component';

describe('DashboardTaskListComponent', () => {
  let component: DashboardTaskListComponent;
  let fixture: ComponentFixture<DashboardTaskListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardTaskListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardTaskListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
