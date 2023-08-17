import { Dayjs } from "dayjs";

export interface userProfileData {
  createdAt: string;
  id: number;
  modifiedAt: string;
  name: string;
  profileImage: string;
}

export interface TrainerListData {
  data: {
    bookmarkCount: number;
    createdAt: string;
    id: string;
    introduction: string;
    profileImg: string;
    profileName: string;
    ptCount: number;
    reviewCount: number;
    revisitGrade: number;
    stars: number;
    tags: string;
    userProfileIdList: Array<number>;
  }[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}

export interface TrainerListDataProps {
  data: {
    bookmarkCount: number;
    createdAt: string;
    id: string;
    introduction: string;
    profileImg: string;
    profileName: string;
    ptCount: number;
    reviewCount: number;
    revisitGrade: number;
    stars: number;
    tags: string;
    userProfileIdList: Array<number>;
  };
}

export interface TrainerProps {
  trainer: string;
}

export interface TrainerDetailData {
  content: string;
  id: string;
  images: Array<string>;
  introduction: string;
  profileImg: string;
  profileName: string;
  tags: string;
  title: string;
  userProfileIdList: Array<number>;
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

export interface DatePickerProps {
  value: Dayjs;
  setValue: Function;
}

export interface ScheduleListItemProps {
  data: {
    trName: string;
    ptName: string;
    ptDate: string;
    ptStartTime: string;
  };
}

export interface PtClassDataProps {
  classId: number;
  liveId: number | null;
  startDate: string;
  startTime: string;
  trainerId: string;
}

export interface ReviewData {
  data: {
    id: number;
    content: string;
    star: number;
    ptClassId: number;
    startDate: string;
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

export interface ReviewDataProps {
  data: {
    id: number;
    content: string;
    star: number;
    ptClassId: number;
    startDate: string;
    profileImg: string;
    profileName: string;
    trainerProfileImg: string;
    trainerProfileName: string;
  };
}

export interface TrainerScheduleData {
  trainerId: string;
  classId: number;
  liveId: number;
  startTime: string;
  startDate: string;
  ptLiveStatus: string;
  exerciseLogStatus: string;
  reviewId: number;
  trainerName: string;
  trainerImage: string;
  userName: string;
  userImage: string;
  introduction: string;
}

export interface UserScheduleData {
  trainerId: string;
  classId: number;
  liveId: number;
  startTime: string;
  startDate: string;
  ptLiveStatus: string;
  exerciseLogStatus: string;
  reviewId: number;
  trainerName: string;
  trainerImage: string;
  userName: string;
  userImage: string;
  introduction: string;
}

export interface SearchFormData {
  keyword?: string;
  date?: string;
  fromTime?: string;
  toTime?: string;
}

export interface LogData {
  title: string;
  contents: {
    image: string;
    text: string;
  }[];
}

export interface TrainerListHeaderProps {
  searchTrainer: (body: SearchFormData) => void;
  sortTrainer: (condition: string) => void;
  sortProps: string;
}

export interface TrainerCategoryData {
  image: string;
  text: string;
}
