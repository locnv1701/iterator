export const getAccessToken = () => localStorage.getItem('token');
export const removeAccessToken = () => {
  localStorage.removeItem('token');
};
export const setAccessToken = (value: string) => {
  localStorage.setItem('token', value);
};

export const getUserId = () => localStorage.getItem('userId');
export const removeUserId = () => {
  localStorage.removeItem('userId');
};
export const setUserId = (value: string) => {
  localStorage.setItem('userId', value);
};

export const getApplicantId = () => localStorage.getItem('applicantId');
export const removeApplicantId = () => {
  localStorage.removeItem('applicantId');
};
export const setApplicantId = (value: string) => {
  localStorage.setItem('applicantId', value);
};

export const getCurrentPage = (userId: string) => localStorage.getItem(userId);
export const removeCurrentPage = (userId: string) => {
  localStorage.removeItem(userId);
};
export const setCurrentPage = (userId: string, value: string) => {
  localStorage.setItem(userId, value);
};

export const getCurrentApplicantPage = (jobId: string) => localStorage.getItem(jobId);
export const removeCurrentApplicantPage = (jobId: string) => {
  localStorage.removeItem(jobId);
};
export const setCurrentApplicantPage = (jobId: string, value: string) => {
  localStorage.setItem(jobId, value);
};
