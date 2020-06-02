import { Gender } from './gender.enum';
import { BasicCharity } from 'src/app/charity/models/basic-charity.model';
import { Volunteering } from 'src/app/charity/models/volunteering.model';
import { Donation } from 'src/app/charity/models/donation.model';

export interface UserInformation {
    id: number;
    username: string;
    name: string;
    age: number;
    gender: Gender;
    location: string;
    ownedCharities: BasicCharity[];
    volunteerings: Volunteering[];
    donations: Donation[];
}
