# YouTube Lite for Android TV

<div align="center">

![Platform](https://img.shields.io/badge/Platform-Android%20TV-green)
![Min SDK](https://img.shields.io/badge/Min%20SDK-21%20(Android%205.0)-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

**YouTube endi qo'llab-quvvatlamaydigan Android TV Box qurilmalari uchun eng yaxshi yechim!**

[Download APK](#-yuklab-olish) | [Features](#-xususiyatlar) | [Installation](#-o'rnatish) | [FAQ](#-savol-javob)

</div>

---

## Nima uchun bu ilova kerak?

Google YouTube ilovasini eski Android TV Box qurilmalarda qo'llab-quvvatlashni to'xtatgan. Bu holda sizda ikki variant bor:

| Variant | Tavsif |
|---------|--------|
| **YouTube Lite** (bu ilova) | Yengil, tez, resurslarni kam sarflaydigan WebView ilova. Eski va zaif qurilmalar uchun ideal. |
| **[SmartTube](https://github.com/yuliskov/SmartTube)** | To'liq funksiyali ilova reklamasiz. Kuchli qurilmalar uchun tavsiya etiladi. |

> **Tavsiya:** Avval YouTube Lite ni sinab ko'ring. Agar qurilmangiz sekin ishlasa yoki muammolar bo'lsa, [SmartTube](https://github.com/yuliskov/SmartTube) ni ishlatib ko'ring.

---

## Yuklab olish

### Oxirgi versiya

| Fayl | Tavsif |
|------|--------|
| [**youtube-lite-tv.apk**](../../releases/latest/download/app-debug.apk) | Debug APK - barcha qurilmalarda ishlaydi |

**Yoki GitHub Releases sahifasidan yuklab oling:**
- [All Releases](../../releases)

### GitHub Actions orqali

Har bir commit dan so'ng avtomatik build bo'ladi:
1. [Actions](../../actions) sahifasiga o'ting
2. Oxirgi muvaffaqiyatli workflow ni tanlang
3. `Artifacts` bo'limidan `debug-apk` ni yuklab oling

---

## Xususiyatlar

### Asosiy imkoniyatlar

- **YouTube TV interfeysi** - Pult bilan boshqarish uchun optimallashtirilgan
- **Yuqori sifatli video** - 4K/8K gacha avtomatik sifat sozlash
- **Yengil va tez** - WebView asosida, resurslarni kam sarflaydi
- **To'liq ekran rejimi** - Immersive fullscreen qo'llab-quvvatlash
- **Cookie va login** - Google hisobingiz bilan kiring

### Pult boshqaruvi

| Tugma | Funksiya |
|-------|----------|
| **DPAD (Yo'nalish)** | Navigatsiya |
| **OK/Enter** | Tanlash |
| **Back** | Orqaga qaytish |
| **Menu/Settings** | Menyu ochish |
| **OK (uzoq bosish)** | Menyu ochish |

### Menyu funksiyalari

- **Home** - Bosh sahifaga qaytish
- **Back/Forward** - Navigatsiya tarixi
- **Reload** - Sahifani yangilash
- **Clear Cookies** - Cookie tozalash (qayta login uchun)
- **Toggle UA** - User-Agent almashtirish (TV/Desktop)
- **Remote Assist** - DPAD ni JavaScript ga yuborish rejimi

---

## O'rnatish

### 1-usul: APK dan to'g'ridan-to'g'ri

1. APK faylni yuklab oling
2. USB orqali TV Box ga ko'chiring
3. Fayl menejeri orqali APK ni oching
4. "Noma'lum manbalardan o'rnatish" ga ruxsat bering
5. O'rnating va ishga tushiring

### 2-usul: ADB orqali

```bash
# ADB ulangan holda
adb install youtube-lite-tv.apk
```

### 3-usul: Downloader ilovasi

1. TV Box ga **Downloader** ilovasini o'rnating
2. APK URL manzilini kiriting
3. Yuklab oling va o'rnating

---

## Talablar

| Talab | Minimal | Tavsiya etilgan |
|-------|---------|-----------------|
| Android versiya | 5.0 (API 21) | 7.0+ |
| RAM | 1 GB | 2 GB+ |
| Internet | Talab qilinadi | Barqaror ulanish |

**Qo'llab-quvvatlanadigan qurilmalar:**
- Android TV Box (barcha brendlar)
- Android TV (Sony, Philips, TCL, va h.k.)
- Fire TV Stick (sideload orqali)
- Mi Box / Mi TV Stick
- NVIDIA Shield
- Boshqa Android TV qurilmalar

---

## Muammolarni hal qilish

### YouTube yuklanmayapti

1. **Internetni tekshiring** - Wi-Fi yoki Ethernet ulanishini tekshiring
2. **Cache tozalang** - Sozlamalar > Ilovalar > YouTube Lite > Cache tozalash
3. **Cookie tozalang** - Ilova menyusidan "Clear Cookies" tugmasini bosing

### Pult ishlamayapti

1. **Remote Assist** rejimini yoqing (menyudan)
2. Ilovani qayta ishga tushiring
3. Pult batareyalarini tekshiring

### Video sekin yuklanmoqda

1. Internet tezligini tekshiring
2. **Toggle UA** tugmasi orqali Desktop rejimiga o'ting
3. Qurilmani qayta ishga tushiring

### Login qilib bo'lmayapti

1. "Clear Cookies" tugmasini bosing
2. Qayta login qiling
3. 2FA yoqilgan bo'lsa, TV kodni ishlatib ko'ring

---

## Alternativa: SmartTube

Agar YouTube Lite sizning qurilmangizda yaxshi ishlamasa yoki qo'shimcha funksiyalar kerak bo'lsa, **SmartTube** ni ishlatishni tavsiya etamiz:

**SmartTube xususiyatlari:**
- Reklamasiz YouTube
- SponsorBlock integratsiyasi
- 4K/8K HDR qo'llab-quvvatlash
- Picture-in-Picture rejimi
- Playback speed nazorati
- Va ko'p boshqa funksiyalar

**Yuklab olish:** [github.com/yuliskov/SmartTube](https://github.com/yuliskov/SmartTube)

> **Eslatma:** SmartTube ko'proq resurs talab qiladi. Zaif qurilmalarda YouTube Lite afzalroq bo'lishi mumkin.

---

## Savol-Javob

<details>
<summary><b>Bu ilova xavfsizmi?</b></summary>

Ha. Ilova ochiq kodli va faqat YouTube.com/tv sahifasini ochadi. Hech qanday ma'lumot to'planmaydi yoki yuborilmaydi.
</details>

<details>
<summary><b>Google hisobim xavfsizmi?</b></summary>

Ha. Login to'g'ridan-to'g'ri YouTube.com orqali amalga oshiriladi. Parolingiz faqat Google serverlariga yuboriladi.
</details>

<details>
<summary><b>Nima uchun ba'zi funksiyalar ishlamayapti?</b></summary>

YouTube TV interfeysi cheklangan funksionallikka ega. To'liq funksiyalar uchun SmartTube ni sinab ko'ring.
</details>

<details>
<summary><b>Ilovani qanday yangilayman?</b></summary>

Yangi versiya chiqqanda GitHub Releases sahifasidan yuklab oling va qayta o'rnating.
</details>

---

## Rivojlantiruvchilar uchun

### Build qilish

```bash
# Clone
git clone https://github.com/XushnazarovFaxriddin/Youtube-Lite-for-Android-TV.git
cd Youtube-Lite-for-Android-TV

# Build debug APK
./gradlew assembleDebug

# APK manzili
# app/build/outputs/apk/debug/app-debug.apk
```

### Loyiha tuzilishi

```
app/src/main/
├── java/com/example/youtubetvlite/
│   └── MainActivity.kt          # Asosiy Activity
├── assets/
│   ├── tv_bridge.js             # Android-JS bridge
│   └── inject_extension.js       # YouTube optimizatsiya
├── res/
│   ├── layout/activity_main.xml  # UI layout
│   ├── drawable/                 # Ikonkalar
│   └── values/                   # Strings, styles
└── AndroidManifest.xml           # App konfiguratsiya
```

---

## Litsenziya

MIT License - Bepul foydalaning, o'zgartiring va tarqating.

---

## Bog'lanish

- **Muammo yoki taklif:** [GitHub Issues](../../issues)
- **Pull Request:** Contributions welcome!

---

<div align="center">

**Agar loyiha yoqgan bo'lsa, yulduzcha bosing!**

Made with ❤️ for Android TV users

</div>
