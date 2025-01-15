// src/app/app.config.ts

import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { JwtInterceptor } from './auth/interceptors/jwt.interceptor'; // Asegúrate de que la ruta es correcta


export const appConfig: ApplicationConfig = {
  providers: [
    importProvidersFrom(HttpClientModule),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
    },
    provideRouter(routes),
    // Otros proveedores globales
  ]
};

