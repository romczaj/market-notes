import {APP_INITIALIZER, ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {KeycloakBearerInterceptor, KeycloakService} from "keycloak-angular";
import {BackendApiService} from "./backend-api/backend-api.service";
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {ConfigService} from "./configuration/config-service";

function initializeKeycloak(keycloak: KeycloakService, configService: ConfigService): () => Promise<boolean> {
  return () => configService.loadConfig()
    .then(() => keycloak.init(
        {
          config: {
            url: configService.config.keycloakUrl,
            realm: 'market-notes',
            clientId: 'market-notes-frontend',
          },
          bearerExcludedUrls: ['/assets', '/clients/public'],
          initOptions: {
            onLoad: 'check-sso',
            silentCheckSsoRedirectUri:
              window.location.origin + '/assets/silent-check-sso.html'
          },
      shouldAddToken: (request) => {
        const {method, url} = request;

        const isGetRequest = 'GET' === method.toUpperCase();
        const acceptablePaths = ['/assets', '/clients/public'];
        const isAcceptablePathMatch = acceptablePaths.some((path) =>
          url.includes(path)
        );

        return !(isGetRequest && isAcceptablePathMatch);
      }}));
}


export const appConfig: ApplicationConfig = {
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService, ConfigService]
    },
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    KeycloakService,
    BackendApiService,
    { provide: HTTP_INTERCEPTORS, useClass: KeycloakBearerInterceptor, multi: true }, provideAnimationsAsync(),
  ],
};
