import { NavLink } from "react-router-dom";
import type { NavigationItem } from "@/shared/types/commerce";

export function NavLinkItem({ to, label }: NavigationItem) {
  return (
    <NavLink
      to={to}
      className={({ isActive }) => `nav-link${isActive ? " is-active" : ""}`}
    >
      {label}
    </NavLink>
  );
}
