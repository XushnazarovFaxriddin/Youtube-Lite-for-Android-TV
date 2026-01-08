# YouTube TV Lite (Android TV WebView app)

Bu project Android TV uchun yengil WebView ilova. Ilova ochilganda `https://www.youtube.com/tv` ni ochadi va pult (DPAD) bilan ishlashga moslangan.

## Nima bor?
- Android TV launcher (LEANBACK) qo'llab-quvvatlash
- Fullscreen / immersive mode
- Pult menyusi (MENU/SETTINGS/INFO yoki DPAD_CENTER long-press)
- `Remote assist` (DPAD'ni web sahifaga `Arrow*` sifatida JS orqali yuborish)
- JS injection: `app/src/main/assets/inject_extension.js`

## APK build
- Windows: `BUILD_APK_WINDOWS.md` faylini o'qing.
- GitHub Actions: `.github/workflows/android-debug.yml` — push qilsangiz debug APK artifact chiqadi.

## Extension logikasini ko'chirish
Chrome extension content-script logikasini `app/src/main/assets/inject_extension.js` ichiga joylashtiring.
