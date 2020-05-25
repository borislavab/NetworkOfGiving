import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor,
    HttpErrorResponse
} from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';

@Injectable()
export class UnauthenticatedResponseInterceptor implements HttpInterceptor {

    private UNAUTHENTICATED_STATUS_CODE = 401;

    constructor(private authService: AuthenticationService,
                private router: Router) { }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        return next.handle(request)
            .pipe(catchError((error: HttpErrorResponse) => {
                if (error.status === this.UNAUTHENTICATED_STATUS_CODE) {
                    this.authService.logout();
                    this.router.navigate(['/login']);
                    return of(null);
                }
                return throwError(error);
            }));
    }
}
