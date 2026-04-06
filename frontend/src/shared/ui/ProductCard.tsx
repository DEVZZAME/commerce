import { Link } from "react-router-dom";
import { formatPrice } from "@/features/catalog/lib/format";
import type { ProductSummary } from "@/shared/types/commerce";
import { ProductToneImage } from "@/shared/ui/ProductToneImage";

export function ProductCard({
  id,
  sellerName,
  name,
  subtitle,
  price,
  badge,
  shippingNote,
  imageTone,
}: ProductSummary) {
  return (
    <article className="product-card">
      <ProductToneImage tone={imageTone} badge={badge} />
      <div className="product-card__body">
        <p className="product-card__eyebrow">{sellerName}</p>
        <h2 className="product-card__title">{name}</h2>
        <p className="product-card__subtitle">{subtitle}</p>
        <div className="product-card__footer">
          <div>
            <strong className="price-text">{formatPrice(price)}</strong>
            <p className="product-card__shipping">{shippingNote}</p>
          </div>
          <Link className="button button--accent" to={`/products/${id}`}>
            상세 보기
          </Link>
        </div>
      </div>
    </article>
  );
}
