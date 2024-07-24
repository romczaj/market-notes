import {Component, inject, model, OnInit, signal} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BackendApiService} from "../backend-api/backend-api.service";
import {CompanyDetailSummaryResponse, UserCompanyNotesResponse} from "../backend-api/responses";
import {MatButton, MatButtonModule} from "@angular/material/button";
import {MatCheckbox} from "@angular/material/checkbox";
import {DecimalPipe, NgIf} from "@angular/common";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {NoteCompanyComment} from "../backend-api/requests";
import {MatList, MatListItem} from "@angular/material/list";
import {CustomDatetimePipe} from "../custom-datetime-pipe";
import {finalize} from "rxjs";
import {UserInvestGoalComponent} from "../user-invest-goal/user-invest-goal.component";

@Component({
  selector: 'app-company-user-details',
  standalone: true,
  imports: [
    MatButton,
    MatCheckbox,
    NgIf,
    MatList,
    MatListItem,
    CustomDatetimePipe,
    UserInvestGoalComponent,
    DecimalPipe
  ],
  templateUrl: './company-user-details.component.html',
  styleUrl: './company-user-details.component.css'
})
export class CompanyUserDetailsComponent implements OnInit{

  constructor(private route: ActivatedRoute,
              private router: Router,
              private backendApiService: BackendApiService,
              private dialog: MatDialog) {
  }

  companyDetails = {} as CompanyDetailSummaryResponse
  userNotes = {} as UserCompanyNotesResponse

  ngOnInit(): void {
    const stockCompanyExternalId = this.route.snapshot.paramMap.get('stockCompanyExternalId');
    if (stockCompanyExternalId !== null) {

      this.backendApiService.getCompanyDetailSummary(stockCompanyExternalId).subscribe({
        next: res => this.companyDetails = res,
        error: err => this.router.navigate(['/home'])
      })

      this.backendApiService.getUserCompanyNotesResponse(stockCompanyExternalId)
        .subscribe({
          next: res => {
            console.log('correct load')
            this.userNotes = res
          },
        })

    } else {
      this.router.navigate(['/home'])
    }
  }

  summaryHideIndicator = signal(false);
  toggleSummaryHideIndicator() {
    this.summaryHideIndicator.update(value => !value);
  }

  commentsHideIndicator = signal(false);
  toggleCommentsHideIndicator() {
    this.commentsHideIndicator.update(value => !value);
  }

  investGoalHideIndicator = signal(false);
  toggleInvestGoalHideIndicator() {
    this.investGoalHideIndicator.update(value => !value);
  }

  protected readonly JSON = JSON;
  protected readonly console = console;

  addCompanyComment() {
    const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      data: {stockCompanyExternalId: this.companyDetails?.basicSummary.stockCompanyExternalId},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result !== undefined) {
        console.log(`process correct ${JSON.stringify(result)}`)
        this.backendApiService.postCompanyComment(result)
          .pipe(finalize(() => this.ngOnInit()))
          .subscribe()
      }
    });
  }
}

@Component({
  selector: 'dialog-overview-example-dialog',
  templateUrl: 'dialog-overview-example-dialog.html',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
  ],
})
export class DialogOverviewExampleDialog {
  readonly dialogRef = inject(MatDialogRef<DialogOverviewExampleDialog>);
  readonly data = inject<NoteCompanyComment>(MAT_DIALOG_DATA);

  readonly noteContent = model(this.data);

  onNoClick(): void {
    this.dialogRef.close();
  }
}
