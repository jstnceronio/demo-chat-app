import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { getAuth, provideAuth } from '@angular/fire/auth';
import { getFirestore, provideFirestore } from '@angular/fire/firestore';
import {provideClientHydration} from "@angular/platform-browser";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    /*
    importProvidersFrom(
      provideFirebaseApp(() => initializeApp(
        {
          "projectId":"idm-chat-2a40d","appId":"1:437636505420:web:3a646bd7f174bf0fea39e9",
          "storageBucket":"idm-chat-2a40d.appspot.com",
          "apiKey":"AIzaSyDypWY7t84RtGId5XwxNIoskEIWhR6Fykc",
          "authDomain":"idm-chat-2a40d.firebaseapp.com",
          "messagingSenderId":"437636505420"})
      ),
      provideAuth(() => getAuth()),
      provideFirestore(() => getFirestore())
    ),*/
  ]
};
