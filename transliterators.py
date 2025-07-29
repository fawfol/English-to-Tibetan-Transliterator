import re

# - Comprehensive Tibetan Transliteration Maps (Wylie-like) -
# This map is designed to handle common Tibetan characters,
# including base consonants, vowels, and some common combinations.
# It prioritizes accuracy for common Wylie transliteration.

# Base Consonants
BASE_CONSONANTS = {
    'ཀ': 'k', 'ཁ': 'kh', 'ག': 'g', 'ང': 'ng',
    'ཅ': 'c', 'ཆ': 'ch', 'ཇ': 'j', 'ཉ': 'ny',
    'ཏ': 't', 'ཐ': 'th', 'ད': 'd', 'ན': 'n',
    'པ': 'p', 'ཕ': 'ph', 'བ': 'b', 'མ': 'm',
    'ཙ': 'ts', 'ཚ': 'tsh', 'ཛ': 'dz', 'ཝ': 'w',
    'ཞ': 'zh', 'ཟ': 'z', 'འ': 'a', 'ཡ': 'y',
    'ར': 'r', 'ལ': 'l', 'ཤ': 'sh', 'ས': 's',
    'ཧ': 'h', 'ཨ': 'a',
}

# Vowel Signs
VOWEL_SIGNS = {
    'ི': 'i', 'ུ': 'u', 'ེ': 'e', 'ོ': 'o',
    # Implicit 'a' is handled by absence of other vowel signs
}

# Subjoined Letters (Yatak, Ratak, Latak, Watak)
SUBJOINED_LETTERS = {
    'ྱ': 'y', # yatak
    'ྲ': 'r', # ratak
    'ླ': 'l', # latak
    'ྭ': 'w', # watak
}

# Prefixes (Ngonjug) - these are usually silent in Wylie but affect structure
# They are handled by combining with the root letter in the Romanization
PREFIXES = {
    'ག': 'g', 'ད': 'd', 'བ': 'b', 'མ': 'm', 'འ': 'a',
}

# Suffixes (Jejugu) - these are pronounced
SUFFIXES = {
    'ག': 'g', 'ང': 'ng', 'ད': 'd', 'ན': 'n', 'བ': 'b', 'མ': 'm', 'འ': '\'', 'ར': 'r', 'ལ': 'l', 'ས': 's',
}

# Second Suffixes (Yangjug) - typically only 'ས'
SECOND_SUFFIX = {
    'ས': 's',
}

# Punctuation and Digits
PUNCTUATION_DIGITS = {
    '།': ' ', # Shad (full stop), often becomes a space
    '་': ' ', # Tsheg (syllable separator), becomes a space for AI
    '༠': '0', '༡': '1', '༢': '2', '༣': '3', '༤': '4',
    '༥': '5', '༦': '6', '༧': '7', '༨': '8', '༩': '9',
    '༄': 'om', '༅': 'bhum', # Om and Bhum symbols (simplified)
    '༆': ' ', # Other common symbols, treat as space
    '༇': ' ',
    '༈': ' ', # Section marker
}

# - Roman to Tibetan Map (for reverse transliteration) -
# This map needs to be ordered by length (longest first) to resolve ambiguities.
# It's crucial to include common combinations to ensure correct reverse mapping.
ROMAN_TO_TIBETAN_MAP = {
    # Common combinations (prioritize longer ones)
    'khy': 'ཁྱ', 'ghy': 'གྷྱ', 'chy': 'ཆྱ', 'jhy': 'ཇྷྱ', 'nyy': 'ཉྱ',
    'phy': 'ཕྱ', 'bhy': 'བྷྱ', 'mhy': 'མྷྱ', 'tsh': 'ཚ', 'dzh': 'ཛྷ',
    'zh': 'ཞ', 'sh': 'ཤ', 'th': 'ཐ', 'dh': 'དྷ', 'ph': 'ཕ', 'bh': 'བྷ',
    'ts': 'ཙ', 'dz': 'ཛ', 'ng': 'ང', 'ny': 'ཉ', 'ch': 'ཆ', 'jh': 'ཇྷ',
    'gy': 'གྱ', 'ky': 'ཀྱ', 'ty': 'ཏྱ', 'dy': 'དྱ', 'py': 'པྱ', 'by': 'བྱ',
    'my': 'མྱ', 'tsy': 'ཙྱ', 'tshy': 'ཚྱ', 'dzy': 'ཛྱ', 'zhy': 'ཞྱ',
    'shy': 'ཤྱ', 'hy': 'ཧྱ', 'ry': 'རྱ', 'ly': 'ལྱ', 'sy': 'སྱ',
    'tr': 'ཏྲ', 'dr': 'དྲ', 'pr': 'པྲ', 'br': 'བྲ', 'mr': 'མྲ',
    'sr': 'སྲ', 'hr': 'ཧྲ',
    'gl': 'གླ', 'pl': 'པླ', 'bl': 'བླ', 'zl': 'ཟླ', 'rl': 'རླ',
    'kl': 'ཀླ', 'sl': 'སླ',
    'kw': 'ཀྭ', 'khw': 'ཁྭ', 'gw': 'གྭ', 'ngw': 'ངྭ',
    'cw': 'ཅྭ', 'chw': 'ཆྭ', 'jw': 'ཇྭ', 'nyw': 'ཉྭ',
    'tw': 'ཏྭ', 'thw': 'ཐྭ', 'dw': 'དྭ', 'nw': 'ནྭ',
    'pw': 'པྭ', 'phw': 'ཕྭ', 'bw': 'བྭ', 'mw': 'མྭ',
    'tsw': 'ཙྭ', 'tshw': 'ཚྭ', 'dzw': 'ཛྭ', 'zhw': 'ཞྭ',
    'zw': 'ཟྭ', 'rw': 'རྭ', 'lw': 'ལྭ', 'shw': 'ཤྭ',
    'sw': 'སྭ', 'hw': 'ཧྭ',
    'kr': 'ཀྲ', 'gr': 'གྲ', 'dr': 'དྲ', 'nr': 'ནྲ', 'pr': 'པྲ', 'br': 'བྲ', 'mr': 'མྲ', 'tsr': 'ཙྲ', 'tshr': 'ཚྲ', 'dzr': 'ཛྲ', 'zhr': 'ཞྲ', 'zr': 'ཟྲ', 'sr': 'སྲ', 'hr': 'ཧྲ',
    'kl': 'ཀླ', 'gl': 'གླ', 'dl': 'དླ', 'nl': 'ནླ', 'pl': 'པླ', 'bl': 'བླ', 'ml': 'མླ', 'tsl': 'ཙླ', 'tshl': 'ཚླ', 'dzl': 'ཛླ', 'zhl': 'ཞླ', 'zl': 'ཟླ', 'sl': 'སླ', 'hl': 'ཧླ',
    'k': 'ཀ', 'kh': 'ཁ', 'g': 'ག', 'ng': 'ང',
    'c': 'ཅ', 'ch': 'ཆ', 'j': 'ཇ', 'ny': 'ཉ',
    't': 'ཏ', 'th': 'ཐ', 'd': 'ད', 'n': 'ན',
    'p': 'པ', 'ph': 'ཕ', 'b': 'བ', 'm': 'མ',
    'ts': 'ཙ', 'tsh': 'ཚ', 'dz': 'ཛ', 'w': 'ཝ',
    'zh': 'ཞ', 'z': 'ཟ', 'y': 'ཡ', 'r': 'ར', 'l': 'ལ', 'sh': 'ཤ', 's': 'ས',
    'h': 'ཧ', 'a': 'ཨ',
    # Vowels
    'i': 'ི', 'u': 'ུ', 'e': 'ེ', 'o': 'ོ',
    # Punctuation and Digits
    ' ': '་', # Space often maps to tsheg or shad
    '0': '༠', '1': '༡', '2': '༢', '3': '༣', '4': '༤',
    '5': '༥', '6': '༦', '7': '༧', '8': '༨', '9': '༩',
    'om': '༄', 'bhum': '༅',
    # Add more specific rules from your Java code
}

# Sort Roman keys by length in descending order for longest-match-first parsing
SORTED_ROMAN_KEYS = sorted(ROMAN_TO_TIBETAN_MAP.keys(), key=len, reverse=True)

def roman_to_tibetan_script(roman_text: str) -> str:
    """
    Converts Romanized Tibetan text (Wylie-like) to Tibetan script.
    This function implements a longest-match-first approach.
    It's a simplified version of a full Wylie-to-Unicode converter.
    """
    tibetan_output = []
    i = 0
    # Normalize input for easier matching (e.g., lowercase)
    roman_text_lower = roman_text.lower()

    while i < len(roman_text_lower):
        matched = False
        # Try to match the longest possible Roman key
        for key in SORTED_ROMAN_KEYS:
            if roman_text_lower.startswith(key, i):
                # Special handling for 'a' to prevent it from consuming implicit 'a'
                # This is a simplification; a full Wylie parser would be more complex.
                if key == 'a' and i > 0 and roman_text_lower[i-1] not in [' ', '\'', '.', '-']: # and not a vowel sign
                    # If 'a' appears after a consonant without explicit separation,
                    # it might be an implicit 'a', so we skip its direct mapping here.
                    # This is a heuristic and might need refinement.
                    continue

                tibetan_output.append(ROMAN_TO_TIBETAN_MAP[key])
                i += len(key)
                matched = True
                break
        if not matched:
            # If no match, append the character as is (e.g., for spaces, punctuation, or unrecognized chars)
            tibetan_output.append(roman_text_lower[i])
            i += 1
    return "".join(tibetan_output)


def tibetan_script_to_roman(tibetan_text: str) -> str:
    """
    Converts Tibetan script to Wylie-like Romanization.
    This function attempts to parse syllables based on Tibetan orthography rules
    (prefixes, root, subjoined, vowel, suffix, second suffix, tsheg).
    """
    roman_output_parts = []
    i = 0
    while i < len(tibetan_text):
        char = tibetan_text[i]

        # Handle punctuation and digits directly
        if char in PUNCTUATION_DIGITS:
            roman_output_parts.append(PUNCTUATION_DIGITS[char])
            i += 1
            continue

        # Try to parse a syllable
        syllable_roman = []
        current_pos = i

        # 1. Check for Prefix (Ngonjug)
        prefix_char = None
        if current_pos < len(tibetan_text) and tibetan_text[current_pos] in PREFIXES:
            prefix_char = tibetan_text[current_pos]
            current_pos += 1

        # 2. Find Root Consonant (or 'a' for vowel-initial syllables)
        root_consonant = None
        if current_pos < len(tibetan_text) and tibetan_text[current_pos] in BASE_CONSONANTS:
            root_consonant = tibetan_text[current_pos]
            current_pos += 1
        elif prefix_char and current_pos < len(tibetan_text) and tibetan_text[current_pos] == 'འ': # Special case for 'འ' as root after prefix
             root_consonant = 'འ'
             current_pos += 1
        elif not prefix_char and current_pos < len(tibetan_text) and tibetan_text[current_pos] == 'ཨ': # 'ཨ' as root
            root_consonant = 'ཨ'
            current_pos += 1
        else:
            # If no root consonant found, it's likely an unrecognized char or parsing error
            roman_output_parts.append(char)
            i += 1
            continue # Move to next char

        # Construct initial Romanization for root (and prefix if present)
        roman_root = ""
        if prefix_char:
            # Wylie often represents prefixes by simply prepending their Romanization
            # without a dot if it forms a valid Wylie stack, otherwise with a dot.
            # For simplicity, we'll just prepend the prefix's Romanization.
            # A more precise Wylie would use a '.' for non-joining prefixes.
            roman_root += PREFIXES[prefix_char]

        roman_root += BASE_CONSONANTS.get(root_consonant, '') # Get Roman for root

        # 3. Check for Subjoined Letters (Yatak, Ratak, Latak, Watak)
        subjoined_roman = ""
        while current_pos < len(tibetan_text) and tibetan_text[current_pos] in SUBJOINED_LETTERS:
            subjoined_roman += SUBJOINED_LETTERS[tibetan_text[current_pos]]
            current_pos += 1

        # Combine root and subjoined
        syllable_roman.append(roman_root + subjoined_roman)

        # 4. Check for Vowel Sign
        vowel_sign = None
        if current_pos < len(tibetan_text) and tibetan_text[current_pos] in VOWEL_SIGNS:
            vowel_sign = tibetan_text[current_pos]
            syllable_roman.append(VOWEL_SIGNS[vowel_sign])
            current_pos += 1
        elif root_consonant == 'ཨ' and not vowel_sign: # 'ཨ' without vowel sign is just 'a'
            syllable_roman.append('a')
        elif not vowel_sign and not subjoined_roman: # Implicit 'a' if no vowel sign and no subjoined letter (for simple cases)
             # This is tricky: Wylie implies 'a' unless another vowel is present.
             # If the base consonant is 'འ' or 'ཨ' and no vowel is present, it's just 'a'.
             # For other consonants, if no vowel sign, it's 'a'.
             if root_consonant != 'ཨ' and root_consonant != 'འ': # 'ཨ' and 'འ' are already handled by their base Romanization
                 syllable_roman.append('a')


        # 5. Check for Suffix (Jejugu)
        suffix_char = None
        if current_pos < len(tibetan_text) and tibetan_text[current_pos] in SUFFIXES:
            suffix_char = tibetan_text[current_pos]
            syllable_roman.append(SUFFIXES[suffix_char])
            current_pos += 1

        # 6. Check for Second Suffix (Yangjug) - typically only 'ས' after certain suffixes
        if suffix_char == 'ས' and current_pos < len(tibetan_text) and tibetan_text[current_pos] in SECOND_SUFFIX:
            # This is a simplification. A full Wylie would check for valid combinations.
            syllable_roman.append(SECOND_SUFFIX[tibetan_text[current_pos]])
            current_pos += 1

        # Join parts of the syllable
        roman_output_parts.append("".join(syllable_roman))

        # Move to the next position in the original Tibetan text
        i = current_pos

    # Final cleanup: remove extra spaces, strip
    return " ".join(filter(None, [part.strip() for part in roman_output_parts])).strip()


# --- Example Usage (for testing your transliterators) ---
if __name__ == "__main__":
    # Test Tibetan to Roman (Wylie-like)
    tibetan_input_1 = "བཀྲ་ཤིས་བདེ་ལེགས།" # Tashi Delek
    roman_output_1 = tibetan_script_to_roman(tibetan_input_1)
    print(f"Tibetan '{tibetan_input_1}' -> Roman '{roman_output_1}'")
    # Expected: bkra shis bde legs

    tibetan_input_2 = "དགོན་པ་" # Gonpa
    roman_output_2 = tibetan_script_to_roman(tibetan_input_2)
    print(f"Tibetan '{tibetan_input_2}' -> Roman '{roman_output_2}'")
    # Expected: dgon pa

    tibetan_input_3 = "རྒྱ་མཚོ་" # Gyatso (ocean)
    roman_output_3 = tibetan_script_to_roman(tibetan_input_3)
    print(f"Tibetan '{tibetan_input_3}' -> Roman '{roman_output_3}'")
    # Expected: rgya mtsho

    tibetan_input_4 = "འགྲོ་" # 'gro (to go)
    roman_output_4 = tibetan_script_to_roman(tibetan_input_4)
    print(f"Tibetan '{tibetan_input_4}' -> Roman '{roman_output_4}'")
    # Expected: 'gro

    tibetan_input_5 = "གཡག་" # g.yag (yak) - Wylie uses g.y for this, our simple prefix logic might not
    roman_output_5 = tibetan_script_to_roman(tibetan_input_5)
    print(f"Tibetan '{tibetan_input_5}' -> Roman '{roman_output_5}'")
    # Current expected: gyag (needs refinement for g.y)

    tibetan_input_6 = "༡༢༣༤༥༦༧༨༩༠" # Numbers
    roman_output_6 = tibetan_script_to_roman(tibetan_input_6)
    print(f"Tibetan '{tibetan_input_6}' -> Roman '{roman_output_6}'")
    # Expected: 1234567890

    # Test Roman to Tibetan
    roman_input_1_rev = "bkra shis bde legs"
    tibetan_output_1_rev = roman_to_tibetan_script(roman_input_1_rev)
    print(f"Roman '{roman_input_1_rev}' -> Tibetan '{tibetan_output_1_rev}'")
    # Expected: བཀྲ་ཤིས་བདེ་ལེགས

    roman_input_2_rev = "dgon pa"
    tibetan_output_2_rev = roman_to_tibetan_script(roman_input_2_rev)
    print(f"Roman '{roman_input_2_rev}' -> Tibetan '{tibetan_output_2_rev}'")
    # Expected: དགོན་པ

    roman_input_3_rev = "rgya mtsho"
    tibetan_output_3_rev = roman_to_tibetan_script(roman_input_3_rev)
    print(f"Roman '{roman_input_3_rev}' -> Tibetan '{tibetan_output_3_rev}'")
    # Expected: རྒྱ་མཚོ
