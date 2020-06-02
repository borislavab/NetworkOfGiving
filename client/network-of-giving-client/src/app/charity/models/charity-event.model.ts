import { CharityEventType } from './event-type.enum';

export interface CharityEvent {
    type: CharityEventType;
    description: string;
    charityId: number;
    timestamp: string;
    date: Date;
}
