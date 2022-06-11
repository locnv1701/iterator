export interface IRequest {
  first_name: string;
  last_name: string;
  job_name: string;
  job_post_id: number;
  request_message: string;
  source_user_id: number;
}

export interface IMatched {
  first_name: string;
  last_name: string;
  job_name: string;
  job_post_id: number;
  request_message: string;
  match_status: EMatchRequestStatus;
  source_user_id: number;
  target_user_id: number;
}

export enum EMatchRequestStatus {
  Approved = 'APPROVED',
  Rejected = 'REJECTED',
}

export interface ISetMatchRequestParam {
  userId: number;
  sourceUserId: number;
  jobPostId: number;
  body: {
    match_status: EMatchRequestStatus;
  };
}
