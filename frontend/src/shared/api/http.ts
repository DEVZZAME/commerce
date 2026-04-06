import { env } from "@/shared/config/env";
import type { ErrorResponse, RestResponse } from "@/shared/types/api";

export class ApiError extends Error {
  status: number;
  code?: string;
  validationErrors?: ErrorResponse["validationErrors"];

  constructor(
    message: string,
    options: {
      status: number;
      code?: string;
      validationErrors?: ErrorResponse["validationErrors"];
    },
  ) {
    super(message);
    this.status = options.status;
    this.code = options.code;
    this.validationErrors = options.validationErrors;
  }
}

function buildUrl(path: string) {
  if (path.startsWith("http")) {
    return path;
  }

  return `${env.apiBaseUrl}${path}`;
}

export async function requestJson<T>(
  path: string,
  init?: RequestInit,
): Promise<T> {
  const response = await fetch(buildUrl(path), {
    ...init,
    headers: {
      "Content-Type": "application/json",
      ...(init?.headers ?? {}),
    },
  });

  const body = (await response.json()) as RestResponse<T | ErrorResponse>;

  if (!response.ok || !body.success) {
    const errorData = body.data as ErrorResponse | null;

    throw new ApiError(body.message || "요청에 실패했습니다.", {
      status: response.status,
      code: errorData?.code,
      validationErrors: errorData?.validationErrors,
    });
  }

  return body.data as T;
}
