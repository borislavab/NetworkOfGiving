import { Charity } from './charity.model';

export interface CharityDetails extends Charity {
    ownerId: number;
    ownerUsername: string;
    ownerName: string;
}
