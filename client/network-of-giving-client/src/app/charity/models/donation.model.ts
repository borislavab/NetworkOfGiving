export interface Donation {
    id: number;
    donatorId: number;
    charityId: number;
    charityTitle: string;
    amount: number;
    timestamp: Date;
}
