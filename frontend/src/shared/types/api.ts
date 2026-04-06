export type RestResponse<T> = {
  success: boolean;
  message: string;
  data: T | null;
};

export type PageResponse<T> = {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  hasNext: boolean;
};

export type ValidationError = {
  field: string;
  message: string;
};

export type ErrorResponse = {
  code: string;
  message: string;
  validationErrors: ValidationError[];
};
