import type { PropsWithChildren, ReactNode } from "react";

type SectionPanelProps = PropsWithChildren<{
  title: string;
  description?: string;
  extra?: ReactNode;
  className?: string;
}>;

export function SectionPanel({
  title,
  description,
  extra,
  className = "",
  children,
}: SectionPanelProps) {
  return (
    <section className={`section-panel ${className}`.trim()}>
      <div className="section-panel__header">
        <div>
          <h2 className="section-panel__title">{title}</h2>
          {description ? (
            <p className="section-panel__description">{description}</p>
          ) : null}
        </div>
        {extra}
      </div>
      {children}
    </section>
  );
}
