type ProductToneImageProps = {
  tone: "copper" | "forest" | "sand" | "charcoal";
  badge?: string;
  compact?: boolean;
};

export function ProductToneImage({
  tone,
  badge,
  compact = false,
}: ProductToneImageProps) {
  const className = compact
    ? `tone-image tone-image--compact tone-image--${tone}`
    : `tone-image tone-image--${tone}`;

  return (
    <div className={className}>
      {badge ? <span className="tone-image__badge">{badge}</span> : null}
    </div>
  );
}
