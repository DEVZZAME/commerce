type LoadingBlockProps = {
  title?: string;
  description?: string;
};

export function LoadingBlock({
  title = "불러오는 중입니다",
  description = "데이터를 가져오는 동안 잠시만 기다려주세요.",
}: LoadingBlockProps) {
  return (
    <section className="feedback-card">
      <div className="feedback-card__spinner" />
      <h2>{title}</h2>
      <p>{description}</p>
    </section>
  );
}
