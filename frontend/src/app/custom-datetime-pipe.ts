import {Pipe, PipeTransform} from '@angular/core';
import {DatePipe} from '@angular/common';

@Pipe({
  standalone: true,
  name: 'customDateTime'
})
export class CustomDatetimePipe extends DatePipe implements PipeTransform {

  override transform = (value: any, args?: any): any => {
    return super.transform(value, "YYYY-MM-dd HH:mm"); // Customize the format as needed
  };
}
