type ErrorBlockProps = {
  title?: string;
  description: string;
  actionLabel?: string;
  onAction?: () => void;
};

export function ErrorBlock({
  title = "문제가 발생했습니다",
  description,
  actionLabel,
  onAction,
}: ErrorBlockProps) {
  return (
    <section className="feedback-card feedback-card--error">
      <h2>{title}</h2>
      <p>{description}</p>
      {actionLabel && onAction ? (
        <button className="button button--secondary" type="button" onClick={onAction}>
          {actionLabel}
        </button>
      ) : null}
    </section>
  );
}
