import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDashboardBaseComponent } from './user-dashboard-base.component';

describe('SuccessLoginComponent', () => {
  let component: UserDashboardBaseComponent;
  let fixture: ComponentFixture<UserDashboardBaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserDashboardBaseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserDashboardBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
