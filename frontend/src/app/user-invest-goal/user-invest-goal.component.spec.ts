import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserInvestGoalComponent } from './user-invest-goal.component';

describe('UserInvestGoalComponent', () => {
  let component: UserInvestGoalComponent;
  let fixture: ComponentFixture<UserInvestGoalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserInvestGoalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserInvestGoalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
