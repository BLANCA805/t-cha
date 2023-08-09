export interface userProfileData {
  createdAt: string;
  id: number;
  modifiedAt: string;
  name: string;
  profileImage: string;
}

export interface TrainerProps {
  trainer: string;
}

export interface TrainerDetailData {
  content: string;
  id: string;
  images: null;
  introduction: string;
  profileImg: string;
  profileName: string;
  tags: string;
  title: string;
}

export interface TrainerReviewData {
  data: {
    id: number;
    content: string;
    star: number;
    profileImg: string;
    profileName: string;
    trainerProfileImg: string;
    trainerProfileName: string;
  }[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}

export interface NoticeData {
  data: {
    id: number;
    status: string;
    title: string;
    content: string;
  }[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}

export interface InquiryData {
  data: {
    questionId: number;
    userProfileId: number;
    title: string;
    content: string;
    name: string;
    userProfileImage: any;
    status: string;
    createdAt: string;
    modifiedAt: string;
  }[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}

export interface BookmarkedTrainerData {
  data: {
    id: number;
    trainerId: string;
    trainerName: string;
    userProfileName: string;
  }[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}

export interface BookmarkedTrainerDataProps {
  data: {
    id: number;
    trainerId: string;
    trainerName: string;
    userProfileName: string;
  };
}

export interface PtRoomData {
  profileId: number;
  liveId: number;
}
