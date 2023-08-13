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
    id: string;
    profileName: string;
    profileImg: string;
    introduction: string;
    tags: string;
    stars: number;
    createdAt: string;
    ptudentCount: number;
    ptCount: number;
    reviewCount: number;
    revisitGrade: number;
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
    id: string;
    profileName: string;
    profileImg: string;
    introduction: string;
    tags: string;
    stars: number;
    createdAt: string;
    ptudentCount: number;
    ptCount: number;
    reviewCount: number;
    revisitGrade: number;
  };
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
    created_at: string;
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
    created_at: string;
  };
}

export interface TrainerScheduleData {
  trainerId: string;
  startTime: string;
  startDate: string;
  liveId: number | null;
  classId: number;
}

export interface UserScheduleData {
  trainerId: string;
  classId: number;
  liveId: number;
  startDate: string;
  startTime: string;
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
