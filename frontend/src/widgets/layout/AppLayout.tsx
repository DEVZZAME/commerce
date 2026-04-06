import { Outlet } from "react-router-dom";
import type { NavigationItem } from "@/shared/types/commerce";
import { AppLogo } from "@/shared/ui/AppLogo";
import { NavLinkItem } from "@/shared/ui/NavLinkItem";

const navigationItems: NavigationItem[] = [
  { to: "/", label: "홈" },
  { to: "/products", label: "상품 목록" },
  { to: "/cart", label: "장바구니" },
];

export function AppLayout() {
  return (
    <div className="site">
      <header className="site-header">
        <div className="site-header__inner">
          <AppLogo />
          <nav className="site-nav">
            {navigationItems.map((item) => (
              <NavLinkItem key={item.to} {...item} />
            ))}
          </nav>
          <a className="button button--secondary" href="/publishing/index.html">
            퍼블리싱 보기
          </a>
        </div>
      </header>
      <div className="site-body">
        <Outlet />
      </div>
    </div>
  );
}
