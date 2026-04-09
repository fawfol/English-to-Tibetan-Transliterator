# English-to-Tibetan-Transliterator

I : Features
```		1.Phonetic typing
			Type Tibetan using English letters (e.g., ka → ཀ, do → གྲོ)
		2.Real-time transliteration engine
			Converts input dynamically using longest-match logic
		3.Syllable-based system
			Handles Tibetan stacking rules, prefixes, and suffixes
		4.Smart suggestion bar
			Prefix suggestions (ག, ད, བ, མ, འ) , Stack suggestions (ར, ལ, ས) : Built dynamically from current input
		5.Tibetan numerals support
			1 → ༡, 2 → ༢, etc.
		6.Dual keyboard modes
			Tibetan transliteration
		7.English typing
			Custom keyboard UI
		Long press space to switch keyboard
		Clean candidate layout using Flexbox
```

II : Installation

		Method 1: Manual APK install
			adb install app-release.apk

		  If error occurs:
			adb uninstall com.entobo.keyboard
			adb install app-release.apk

		Method 2: Enable on device
			Open Settings
			Go to Keyboard list and default
			Enable En2Bo Transliterator
			Set it as default keyboard

III : Project Structure
		app/
		├── java/com/entobo/keyboard/
		│   ├── TibetanIME.java          # Main keyboard service
		│   ├── TransliterationEngine.java  # Core logic
		│   ├── MainActivity.java        # App UI (info + links)
		│   └── CustomKeyboardView.java  # Custom keyboard rendering
		│
		├── res/
		│   ├── layout/                  # Keyboard + candidate views
		│   ├── xml/                     # Keyboard layouts
		│   └── values/                  # Strings, styles

IV : Motivation

		“A culture lives through its language.”

		As a Tibetan refugee, this project is deeply personal.

		Typing Tibetan on mobile is difficult due to:

		complex stacking rules
		lack of intuitive tools

		This keyboard aims to:

		make Tibetan typing fast and natural
		encourage younger generations to use Tibetan daily
		strengthen cultural identity through digital use

V : Contributing

		Contributions are welcome!

		You can help with:

		improving transliteration accuracy
		optimizing suggestion logic
		UI/UX improvements
		adding Tibetan linguistic rules

VI : Contact
		🌐 Website: https://tenzinkalsang.online
		📧 Email: kalsangkalsang5@gmail.com
