# Robotta Rebuild v1

This is the **clean rebuild foundation** derived from the uploaded legacy APK/source archives.

## Important
- This is **not** a direct decompiled-source build.
- Legacy credentials, API keys, cookies, session tokens and other secrets are intentionally excluded.
- Package is `com.robotta.bot.rebuild` so debug builds can coexist with the old app.
- The first milestone is intentionally small: Gradle project + navigation + WebView foundation + foreground service.

## Build
Requires Android SDK with API 35 and a compatible JDK (17 recommended).

```bash
gradle :app:assembleDebug
```

APK output:
`app/build/outputs/apk/debug/app-debug.apk`

## Next modules
1. Data model / import-export
2. Auto Frame image pipeline
3. Account/session storage
4. Facebook WebView abstraction
5. Marketplace workflow
6. Group research/scrape workflow
7. Messenger profiles
8. AI provider abstraction
9. Background job orchestration
10. Test and hardening
