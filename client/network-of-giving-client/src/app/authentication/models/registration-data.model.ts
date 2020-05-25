import { Gender } from './gender.enum';

export interface RegistrationData {
    username: string;
    password: string;
    name: string;
    age: number;
    gender: Gender;
    location: string;
}
