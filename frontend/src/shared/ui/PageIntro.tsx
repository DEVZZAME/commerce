type PageIntroProps = {
  eyebrow: string;
  title: string;
  description: string;
  actionLabel?: string;
};

export function PageIntro({
  eyebrow,
  title,
  description,
  actionLabel,
}: PageIntroProps) {
  return (
    <section className="page-intro">
      <p className="page-intro__eyebrow">{eyebrow}</p>
      <h1 className="page-intro__title">{title}</h1>
      <p className="page-intro__description">{description}</p>
      {actionLabel ? <span className="page-intro__action">{actionLabel}</span> : null}
    </section>
  );
}
