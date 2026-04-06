import type { PropsWithChildren } from "react";

type SummaryCardProps = PropsWithChildren<{
  title: string;
}>;

export function SummaryCard({ title, children }: SummaryCardProps) {
  return (
    <aside className="summary-card">
      <h2 className="summary-card__title">{title}</h2>
      {children}
    </aside>
  );
}
