import { IExperience, IJobPost, ISkill } from 'features/UserProfile/types/userProfileTypes';

export type ExampleType = {
  value: string;
  title: string;
};

export interface IExtendedJobPost extends IJobPost {
  image: string;
}

export enum EGender {
  Female = 'FEMALE',
  Male = 'MALE',
}

export interface IApplicantCard {
  user_id: number;
  first_name: string;
  last_name: string;
  age: number;
  gender: EGender;
  location: string;
  entry_level: string;
  experience_year: number;
  experiences: IExperience[];
  skills: ISkill[];
  image?: string;
}

export interface IApplicantQuery {
  entry_level?: string;
  experience_year?: number;
}

export interface IJobPostQuery {
  job_type?: string;
  job_salary?: number;
  job_location?: string;
}
