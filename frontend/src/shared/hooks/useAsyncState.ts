import { useState } from "react";

type AsyncStatus = "idle" | "loading" | "success" | "error";

export function useAsyncState() {
  const [status, setStatus] = useState<AsyncStatus>("idle");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  return {
    status,
    errorMessage,
    start() {
      setStatus("loading");
      setErrorMessage(null);
    },
    succeed() {
      setStatus("success");
      setErrorMessage(null);
    },
    fail(message: string) {
      setStatus("error");
      setErrorMessage(message);
    },
  };
}
