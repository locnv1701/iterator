export interface IProfileState {
  userProfile: IProfile;
  education?: IEducation;
  skillSet?: ISkill[];
  experiences?: IExperience[];
  sharerJobPost: IJobPost[];
}

export interface IJobPost {
  job_post_id?: number;
  sharer_id: number;
  job_type: string;
  job_salary: number;
  job_description: string;
  job_location: string;
  company: {
    company_name: string;
    business_stream: string;
    profile_description: string;
  };
  is_available: boolean;
}

export interface IProfile {
  first_name: string;
  last_name: string;
  entry_level: string;
  phone_number: string;
  experience_year: number;
  email: string;
  github_link?: string;
  facebook_link?: string;
  instagram_link?: string;
  twitter_link?: string;
  profile_display_name?: string;
  age?: number;
  gender?: string;
  location?: string;
  status?: string;
}

export interface ISocialMedia {
  facebook: string;
  instagram: string;
  twitter: string;
  github: string;
}

export interface IUserInfo {
  email: string;
  phone_number: string;
  first_name: string;
  last_name: string;
  location: string;
  entry_level: string;
}

export interface IEducation {
  major: string;
  college_name: string;
  starting_date?: string;
  completion_date?: string;
  cgpa?: number;
  degree_name?: string;
}

export interface ISkill {
  skill_name: string;
  level: number;
}

export interface IExperience {
  job_title: string;
  company_name: string;
  start_date: string;
  end_date: string;
  project_self_description: string;
}
