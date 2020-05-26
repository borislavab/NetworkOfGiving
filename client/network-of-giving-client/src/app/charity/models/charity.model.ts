export interface Charity {
    id: number;
    title: string;
    description: string;
    amountRequired: number;
    amountCollected: number;
    volunteersRequired: number;
    volunteersApplied: number;
    thumbnail: Uint8Array;
}