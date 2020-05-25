import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../services/token.service';
import { environment } from 'src/environments/environment';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const validTokenExists = this.tokenService.hasValidToken();
    const requestToApiUrl = request.url.startsWith(environment.apiUrl);
    if (validTokenExists && requestToApiUrl) {
      const token = this.tokenService.token;
      const tokenType = this.tokenService.tokenType;
      request = request.clone({
        setHeaders: {
            Authorization: `${tokenType} ${token}`
        }
      });
    }
    return next.handle(request);
  }
}
