import {Component, inject, Input} from '@angular/core';
import {InvestGoalModel} from "../backend-api/responses";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {BackendApiService} from "../backend-api/backend-api.service";

@Component({
  selector: 'app-user-invest-goal',
  standalone: true,
  imports: [
    MatLabel,
    MatFormField,
    FormsModule,
    MatButton,
    MatInput
  ],
  templateUrl: './user-invest-goal.component.html',
  styleUrl: './user-invest-goal.component.css'
})
export class UserInvestGoalComponent {

  @Input() investGoalModel = {} as InvestGoalModel;

  backendApiService = inject(BackendApiService);

  submitForm() {
    console.log(`investGoalModel ${JSON.stringify(this.investGoalModel)}`)
    if (this.investGoalModel !== undefined) {
      this.backendApiService.postInvestGoal(this.investGoalModel).subscribe()
    }
  }
}
