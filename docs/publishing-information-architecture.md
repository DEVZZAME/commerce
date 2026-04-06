# Commerce Publishing Information Architecture

## Goal

4단계 퍼블리싱은 React 구현 전에 이커머스 화면의 정보 구조, 사용자 흐름, 공통 레이아웃 규칙을 먼저 확정하는 단계다.

## Primary User Flows

1. Discover products
   - Landing navigation
   - Product list
   - Category and filter refinement
   - Product detail
2. Configure a purchase
   - Option selection
   - Quantity decision
   - Add to cart
3. Review basket
   - Cart item review
   - Delivery estimate review
   - Price summary
4. Complete checkout
   - Shipping information
   - Payment selection
   - Order summary confirmation

## Screen Map

### 1. Product List

- Hero banner for campaign context
- Category tabs
- Filter rail
- Product grid
- Curated editorial callout

### 2. Product Detail

- Media gallery
- Product headline and pricing
- Seller trust block
- Option selector
- Delivery and return policy
- Sticky purchase summary

### 3. Cart

- Cart item list
- Shipping grouping
- Price breakdown
- Promotional suggestion strip
- Checkout CTA

### 4. Checkout

- Progress indicator
- Shipping form
- Delivery memo / timing
- Payment method
- Final order summary

### 5. UI Kit

- Buttons
- Chips
- Cards
- Form fields
- Summary tiles
- Status badges

## Layout Rules

- Header is fixed across all publishing pages.
- Main content uses a centered shell with max width 1280px.
- Desktop uses a two-column rhythm for dense commerce pages.
- Tablet collapses secondary rails below primary content.
- Mobile switches to stacked sections with sticky bottom actions.

## Visual Direction

- Keyword: editorial market
- Tone: warm ivory background with copper and forest accents
- Card system: rounded panels with soft elevation and strong spacing
- Typography:
  - display: `Avenir Next`, `Segoe UI`, sans-serif
  - body: `IBM Plex Sans KR`, `Apple SD Gothic Neo`, `Noto Sans KR`, sans-serif

## Shared Components

- Global header
- Page intro block
- Section title
- Filter chip
- Product card
- Price block
- Order summary card
- Input field / select field / radio card
- Primary and secondary CTA

## Responsive Rules

- Desktop: `1280px` max content width
- Tablet breakpoint: `1024px`
- Mobile breakpoint: `720px`
- Mobile action areas use sticky bottom bars where purchase intent is critical

## Publishing Deliverables

- `/publishing/index.html`
- `/publishing/product-list.html`
- `/publishing/product-detail.html`
- `/publishing/cart.html`
- `/publishing/checkout.html`
- `/publishing/ui-kit.html`
- Shared CSS tokens and component styles
