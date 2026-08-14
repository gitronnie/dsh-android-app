# DeepSeek Harness Android Design System

This file is the persistent visual source of truth. The reference APKS was used only to study hierarchy, spacing, navigation and motion; DeepSeek Harness keeps its own name, whale mark, colors and implementation.

## Direction

- Product: mobile AI coding client and file workspace.
- Style: warm, editorial, content-first, low chrome and work-focused.
- Density: compact-balanced; chat favors visible content while controls remain comfortably touchable.
- Corners: 4-8dp only. Cards are reserved for true repeated items, dialogs and framed editors.

## Color

| Role | Light | Dark |
| --- | --- | --- |
| Background | `#F9F9F7` | `#181816` |
| Surface variant | `#F0EFEC` | `#2A2A27` |
| Primary text/action | `#131313` | `#F3F2ED` |
| Secondary action | `#006A60` | `#83D5C8` |
| Tertiary warning | `#C6613F` | `#FFB59B` |
| Muted text | `#74726D` | `#BEBBB3` |

Purple is not a product color. Components consume Material 3 semantic tokens from `Theme.kt`; screens do not invent local palettes.

## Typography

- Display and screen headings use the platform serif family at 20-29sp.
- Product title, controls and body use the platform sans family at 12-18sp.
- Primary chat copy is 15sp with 22sp line height; secondary labels are 12-14sp. Letter spacing is always 0sp.
- Markdown establishes hierarchy with typography and spacing, not nested cards.

## Layout

- Mobile navigation uses a modal drawer so conversation content owns the bottom edge.
- Wide layouts use a stable fixed sidebar and two-pane chat/file views where useful.
- Repeated settings, sessions and files are full-width rows separated by subtle dividers.
- Creation, model choice and provider editing use Material 3 modal bottom sheets.
- Icon controls are at least 48dp touch targets; the circular send action is 44dp with surrounding spacing.
- Activity `adjustResize` exposes the IME height. `Scaffold` content consumes padding it has already applied, then the chat column applies only the remaining IME inset; this keeps the composer just above the keyboard without duplicate blank space. Constrained row weights prevent overlap on narrow screens.
- The chat composer uses a 44dp text area and compact surrounding padding; send and navigation actions retain their 44/48dp touch targets.

## Motion

- Screen changes use 180-260ms fade and short spatial slide transitions.
- Bottom sheets and drawers use Material motion supplied by Compose.
- Press feedback changes color/elevation without resizing layout.
- No autoplaying or decorative animation; reduced-motion platform behavior remains authoritative.

## Interaction

- Errors appear in a Snackbar and never interrupt streaming with a modal dialog.
- Rapid WebSocket events are coalesced; loading indicators reflect real work without layout shifts.
- Standalone icons have localized content descriptions and unfamiliar actions have tooltips.
- Disabled state is visible but labels remain legible in light and dark themes.

## Forbidden

- No copied third-party trademarks, icons, fonts or assets.
- No bottom navigation, oversized dashboard cards, purple/blue gradients, decorative blobs or emoji icons.
- No raw event JSON in chat, raw Markdown markers in assistant content or secrets echoed into forms.
- No hard-coded colors in screen components and no shapes above 8dp.

## Pre-Delivery

- Verify 360dp/412dp phones and a wide viewport with no clipped text or controls.
- Verify default Chinese and English, light/dark themes, font scaling, keyboard and system back.
- Verify drawer, sheets, model picker, provider form, file actions and Snackbar touch targets.
- Install the signed release APK and inspect screenshots plus UI hierarchy after every visual refactor.
