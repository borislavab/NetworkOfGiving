import { BasicCharity } from './basic-charity.model';

export interface Charity extends BasicCharity {
    description: string;
    amountRequired: number;
    amountCollected: number;
    volunteersRequired: number;
    volunteersApplied: number;
    thumbnail: string;
    createdAt: Date;
}
