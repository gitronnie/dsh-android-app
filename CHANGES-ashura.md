# CHANGES-ashura — dsh-android-app (Ashura fork)

Delta ledger for the Ashura Android fork of `Hakunm/dsh-android-app`.
Each entry records phase / date / upstream-SHA / reason so Ashura-specific changes
stay separable from upstream and upstream fixes can be pulled cleanly.

## Provenance

- Upstream: `Hakunm/dsh-android-app` (AGPL-3.0), default branch `main`.
- Upstream pinned HEAD (at fork): `b0bf711` — "Release v1.0.0".
- Local fork `origin`: `gitronnie/dsh-android-app`.
- License file: `LICENSE` (GNU AGPL v3, 2007) at repo root. Fork is AGPL-3.0-derived;
  Ashura-specific reuse is under the AGPL, per operator decision GO fork-and-extend
  (no clean-room).

## Log

_(Entries appended below as changes land.)_

### 000 — initial fork hygiene (2026-09)

- Fork + clone upstream into `~/sources/dsh-android-app`; added this ledger.
- No product-source changes in this entry.

### 001 — buildability shim (2026-09) — M2

- `gradlew` was committed upstream as mode `100644` (non-executable); `chmod +x` the
  wrapper so `./gradlew` runs. Exec-bit change only; no content. Reason: upstream wart that
  breaks every headless run on a fresh clone.
- Verified M2 debug gate on the **unmodified upstream**:
  `./gradlew testDebugUnitTest lintDebug assembleDebug` → BUILD SUCCESSFUL (JDK 17
  Temurin 17.0.20.1, wrapper Gradle 9.5.0, SDK android-36). Debug output is deliberately
  unsigned (`app-debug-unsigned.apk`): upstream sets `debug.signingConfig = null` so AGP
  emits an unsigned debug APK; `verifyDedicatedReleaseSigning` gates only
  `packageRelease`/`bundleRelease`, not `assembleDebug`.
- M2a′ dependency scan (this fork): no OWASP/SBOM/Gradle vuln tooling is wired, so a
  grounded manual review of the resolved `releaseRuntimeClasspath` was run. **No
  High/Critical REACHABLE CVE.** OkHttp resolves 4.12.0, Okio 3.6.0 (both above the
  CVE-2023-3635 Okio fix floor of 3.4.0); Compose BOM 2026.06.00, Kotlin 2.3.21,
  kotlinx-serialization 1.9.0, coroutines 1.10.2, androidx core 1.18.0 current.

### 002 — REBRAND: product identity to "Ashura" (2026-09) — M2 rebrand delta

Upstream-SHA-baseline: `b0bf711`.

Product-identity surface (user-visible only; no private-logic rewiring):
- `app/src/main/res/values/strings.xml` (zh, default): `app_name` and `connect_title`
  → "Ashura" / "连接 Ashura".
- `app/src/main/res/values-en/strings.xml` (en): `app_name` and `connect_title`
  → "Ashura" / "Connect to Ashura".
- `app_name` renders the launcher label AND the in-app nav/app title (App.kt:132, 247);
  `connect_title` renders the connect/onboarding screen heading (ConnectScreen.kt:80).
- Default "server hint" (example connect address) → clearly-PLACEHOLDER local value:
  `HarnessViewModel.kt` `endpointDraft` default and `ConnectScreen.kt` placeholder both
  now `http://127.0.0.1:3090` (replacing upstream's example `http://192.168.1.20:3090`).
  The REAL host/port/root is a config-default the OPERATOR patches at deploy; the operator
  alone knows the real address. Do not ship anything but the loopback placeholder here.
- Deliberately UNCHANGED (documented intent, not oversight): the Java/Kotlin namespace
  `io.github.hakunm.deepseekharness`, `DeepSeekHarnessApp`/`DeepSeekHarnessTheme`/
  `DeepSeekTeal` identifiers and `Theme.DeepSeekHarness` style key are INTERNAL/structural,
  not the user-visible product name; renaming them would churn many files and fight clean
  upstream pulls. `dhs_workspace`/`DSH WebUI`-type strings describe the remote server type
  the client connects to (which genuinely is the DSH WebUI server) and stay accurate.

Re-verified debug gate after rebrand: `./gradlew testDebugUnitTest lintDebug assembleDebug`
= PASS (recorded in provenance/verification; this entry is committed with the rebrand).
