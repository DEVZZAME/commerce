export const env = {
  apiBaseUrl: import.meta.env.VITE_API_BASE_URL?.trim() || "/api",
  useMockOrder: import.meta.env.VITE_USE_MOCK_ORDER !== "false",
};
