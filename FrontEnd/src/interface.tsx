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
    id: string;
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
