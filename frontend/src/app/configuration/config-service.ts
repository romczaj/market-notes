import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {firstValueFrom} from "rxjs";
import {ApplicationProperties} from "./application-properties";

@Injectable({
  providedIn: 'root',
})
export class ConfigService {

  private http = inject(HttpClient);

  private _config = {} as ApplicationProperties;

  get config(): ApplicationProperties {
    return this._config
  }

  loadConfig(): Promise<any> {
    return this.fetchConfigFromAssets('./assets/config/application-properties.json')
  }

  private async fetchConfigFromAssets(path: string): Promise<any> {
    return firstValueFrom(this.http.get<ApplicationProperties>(path))
      .then(data => this._config = data)
      .catch(e => console.log(e))
      .finally(() => console.log(`config loaded ${JSON.stringify(this._config)}`))
  }
}

