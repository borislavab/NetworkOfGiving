import { TestBed } from '@angular/core/testing';

import { UnauthenticatedResponseInterceptor } from './unauthenticated-response.interceptor';

describe('UnauthenticatedResponseInterceptor', () => {
    beforeEach(() => TestBed.configureTestingModule({
        providers: [
            UnauthenticatedResponseInterceptor
        ]
    }));

    it('should be created', () => {
        const interceptor: UnauthenticatedResponseInterceptor = TestBed.inject(UnauthenticatedResponseInterceptor);
        expect(interceptor).toBeTruthy();
    });
});
