import { startTransition, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { fetchProducts } from "@/features/catalog/api/productApi";
import { productSummaries } from "@/features/catalog/data/mockProducts";
import { mapProductSummary } from "@/features/catalog/lib/productMapper";
import { useAsyncState } from "@/shared/hooks/useAsyncState";
import type { ProductSummary } from "@/shared/types/commerce";
import { ErrorBlock } from "@/shared/ui/ErrorBlock";
import { LoadingBlock } from "@/shared/ui/LoadingBlock";
import { PageIntro } from "@/shared/ui/PageIntro";
import { ProductCard } from "@/shared/ui/ProductCard";
import { SectionPanel } from "@/shared/ui/SectionPanel";

const categoryFilters = ["전체", "키보드", "디스플레이", "정리"];

export function ProductListPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const [products, setProducts] = useState<ProductSummary[]>([]);
  const [keywordInput, setKeywordInput] = useState(searchParams.get("keyword") ?? "");
  const [notice, setNotice] = useState<string | null>(null);
  const [reloadToken, setReloadToken] = useState(0);
  const asyncState = useAsyncState();

  useEffect(() => {
    setKeywordInput(searchParams.get("keyword") ?? "");
  }, [searchParams]);

  useEffect(() => {
    async function loadProducts() {
      asyncState.start();

      try {
        const response = await fetchProducts({
          keyword: searchParams.get("keyword") || undefined,
          sortBy: (searchParams.get("sortBy") as "createdAt" | "price" | "name" | null) ?? "createdAt",
          direction: (searchParams.get("direction") as "ASC" | "DESC" | null) ?? "DESC",
          page: 0,
          size: 12,
        });

        if (response.content.length === 0) {
          setProducts(productSummaries);
          setNotice("백엔드 상품이 아직 없어 샘플 데이터를 함께 보여줍니다.");
        } else {
          setProducts(response.content.map(mapProductSummary));
          setNotice(null);
        }

        asyncState.succeed();
      } catch (error) {
        setProducts(productSummaries);
        setNotice("백엔드 연결에 실패해 샘플 데이터를 표시합니다.");
        asyncState.fail(error instanceof Error ? error.message : "상품을 불러오지 못했습니다.");
      }
    }

    void loadProducts();
  }, [reloadToken, searchParams]);

  function handleSearchSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    startTransition(() => {
      const nextParams = new URLSearchParams(searchParams);

      if (keywordInput.trim()) {
        nextParams.set("keyword", keywordInput.trim());
      } else {
        nextParams.delete("keyword");
      }

      setSearchParams(nextParams);
    });
  }

  return (
    <main className="page-stack">
      <PageIntro
        eyebrow="상품 목록"
        title="한눈에 탐색하고 바로 비교할 수 있는 목록 화면"
        description="백엔드 상품 조회 API와 연결되어 있으며, 개발 중에는 빈 목록이나 연결 실패 시 샘플 데이터를 같이 보여줍니다."
      />

      <SectionPanel title="검색과 정렬" description="키워드와 정렬 기준은 실제 상품 목록 API 쿼리로 전달됩니다.">
        <form className="toolbar" onSubmit={handleSearchSubmit}>
          <input
            className="toolbar__input"
            placeholder="상품명 또는 셀러명을 입력하세요"
            value={keywordInput}
            onChange={(event) => setKeywordInput(event.target.value)}
          />
          <button className="button button--accent" type="submit">
            검색
          </button>
        </form>
        <div className="chip-row" style={{ marginTop: 16 }}>
          {categoryFilters.map((filter, index) => (
            <span key={filter} className={`chip${index === 0 ? " chip--active" : ""}`}>
              {filter}
            </span>
          ))}
        </div>
        {notice ? <p className="section-panel__description">{notice}</p> : null}
      </SectionPanel>

      {asyncState.status === "loading" ? <LoadingBlock /> : null}

      {asyncState.status === "error" && products.length === 0 ? (
        <ErrorBlock
          description={asyncState.errorMessage ?? "상품을 불러오지 못했습니다."}
          actionLabel="다시 시도"
          onAction={() => {
            setReloadToken((current) => current + 1);
          }}
        />
      ) : null}

      {products.length > 0 ? (
        <section className="product-grid">
          {products.map((product) => (
            <ProductCard key={product.id} {...product} />
          ))}
        </section>
      ) : null}
    </main>
  );
}
