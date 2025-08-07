import gradio as gr

# Keys are sorted by length descending in the processing function
# to ensure the longest possible match is found first (e.g., 'khya' before 'khy' or 'k').
TIBETAN_MAP = {
     # Consonants + vowels
    "ka": "ཀ",   "kha": "ཁ",   "gha": "ག",   "nga": "ང",
    "ca": "ཅ",   "cha": "ཆ",   "ja": "ཇ",   "jha": "ཇ",   "nya": "ཉ",
    "ta": "ཏ",   "tha": "ཐ",   "dha": "ད",   "na": "ན",
    "pa": "པ",   "pha": "ཕ",   "ba": "བ",   "bha": "བ",   "ma": "མ",
    "tza": "ཙ",  "tzsa": "ཙ",  "tsza": "ཙ",  "tssa": "ཙ",  "tsa": "ཚ",   "za": "ཛ",   "wa": "ཝ",
    "zha": "ཞ",  "sza": "ཟ",   "ya": "ཡ",   "ra": "ར",
    "la": "ལ",   "sha": "ཤ",   "sa": "ས",
    "ha": "ཧ",   "a": "ཨ",

    # Syllable-final (ending) consonants ('jejug chuu')
    "g": "ག",    "ng": "ང",    "n": "ན",    "b": "བ",
    "aa": "འ",   "m": "མ",     "l": "ལ",    "r": "ར",
    "s": "ས",    "p": "པ",     "k": "ག",    "dh": "ད",

    # Vowel diacritics (applied to consonants)
    "ki": "ཀི",   "ku": "ཀུ",   "ke": "ཀེ",   "ko": "ཀོ",
    "khi": "ཁི",  "khu": "ཁུ",  "khe": "ཁེ",  "kho": "ཁོ",
    "gi": "གི",   "gu": "གུ",   "ge": "གེ",   "go": "གོ",
    "ghi": "གི",  "ghu": "གུ",  "ghe": "གེ",  "gho": "གོ",
    "ngi": "ངི",  "ngu": "ངུ",  "nge": "ངེ",  "ngo": "ངོ",
    "chhi": "ཅི", "chhu": "ཅུ", "chhe": "ཅེ", "chho": "ཅོ",
    "chi": "ཆི",  "chu": "ཆུ",  "che": "ཆེ",  "cho": "ཆོ",
    "jhi": "ཇི",  "jhu": "ཇུ",  "jhe": "ཇེ",  "jho": "ཇོ",
    "nyi": "ཉི",  "nyu": "ཉུ",  "nye": "ཉེ",  "nyo": "ཉོ",
    "ti": "ཏི",   "tu": "ཏུ",   "te": "ཏེ",   "to": "ཏོ",
    "thi": "ཐི",  "thu": "ཐུ",  "the": "ཐེ",  "tho": "ཐོ",
    "di": "དི",   "du": "དུ",   "de": "དེ",   "do": "དོ",
    "dhi": "དི",  "dhu": "དུ",  "dhe": "དེ",  "dho": "དོ",
    "ni": "ནི",   "nu": "ནུ",   "ne": "ནེ",   "no": "ནོ",
    "pi": "པི",   "pu": "པུ",   "pe": "པེ",   "po": "པོ",
    "phi": "ཕི",  "phu": "ཕུ",  "phe": "ཕེ",  "pho": "ཕོ",
    "bhi": "བི",  "bhu": "བུ",  "bhe": "བེ",  "bho": "བོ",
    "mi": "མི",   "mu": "མུ",   "me": "མེ",   "mo": "མོ",
    
    "tzi": "ཙི",  "tzu": "ཙུ",  "tze": "ཙེ",  "tzo": "ཙོ",
    "tssi": "ཙི",  "tssu": "ཙུ",  "tsse": "ཙེ",  "tsso": "ཙོ",
    "tzsi": "ཙི",  "tzsu": "ཙུ",  "tzse": "ཙེ",  "tzso": "ཙོ",
    "tszi": "ཙི",  "tszu": "ཙུ",  "tsze": "ཙེ",  "tszo": "ཙོ",
    
    "tsi": "ཚི",  "tsu": "ཚུ",  "tse": "ཚེ",  "tso": "ཚོ",
    "zi": "ཛི ",  "zu": "ཛུ",   "ze": "ཛེ",   "zo": "ཛོ",
    "zhi": "ཞི",  "zhu": "ཞུ",  "zhe": "ཞེ",  "zho": "ཞོ",
    "szi": "ཟི",  "szu": "ཟུ",  "sze": "ཟེ",  "szo": "ཟོ",
    "wi": "ཝི",   "wu": "ཝུ",   "we": "ཝེ",   "wo": "ཝོ",
    "yi": "ཡི",   "yu": "ཡུ",   "ye": "ཡེ",   "yo": "ཡོ",
    "ri": "རི",   "ru": "རུ",   "re": "རེ",   "ro": "རོ",
    "li": "ལི",   "lu": "ལུ",   "le": "ལེ",   "lo": "ལོ",
    "shi": "ཤི",  "shu": "ཤུ",  "she": "ཤེ",  "sho": "ཤོ",
    "si": "སི",   "su": "སུ",   "se": "སེ",   "so": "སོ",
    "hi": "ཧི",   "hu": "ཧུ",   "he": "ཧེ",   "ho": "ཧོ",
  
    "i": "ཨི",   "u": "ཨུ",   "e": "ཨེ",   "o": "ཨོ",

    # Subjoined 'ya' ('yatak')
    "kya": "ཀྱ",  "kyi": "ཀྱི",  "kyu": "ཀྱུ",  "kye": "ཀྱེ",  "kyo": "ཀྱོ",
    "khya": "ཁྱ", "khyi": "ཁྱི",  "khyu": "ཁྱུ",  "khye": "ཁྱེ",  "khy": "ཁྱི",
    "gya": "གྱ",  "gyi": "གྱི",  "ghyi": "གྱི",  "gyu": "གྱུ",  "gye": "གྱེ",  "gyo": "གྱོ",
    "chhya": "པྱ", "chhyi": "པྱི", "chhyu": "པྱུ", "chhye": "པྱེ", "chhyo": "པྱོ",
    "chya": "ཕྱ",  "chyi": "ཕྱི",  "chyu": "ཕྱུ",  "chye": "ཕྱེ",  "chyo": "ཕྱོ",
    "jhya": "བྱ",  "jhyi": "བྱི",  "jhyu": "བྱུ",  "jhye": "བྱེ",  "jhyo": "བྱོ",
    "nyya": "མྱ",  "nyyi": "མྱི",  "nyyu": "མྱུ",  "nyye": "མྱེ",  "nyyo": "མྱོ",

    # Suffix letters (Jejug - འཇུག་པའི་ཡི་གེ)
    # These are consonants at the end of a syllable.
    "g": "ག", "ng": "ང", "d": "ད", "n": "ན", "b": "བ", "m": "མ",
    "'": "འ", "r": "ར", "l": "ལ", "s": "ས",
    # Historical/less common suffixes
    "k": "ཀ", "p":"པ"
}
# Get the keys and sort them by length, descending.
# This ensures "kha" is matched before "k", "ng" before "n", etc.
SORTED_KEYS = sorted(TIBETAN_MAP.keys(), key=len, reverse=True)

def transliterate(roman_string):
    """
    Transliterates a Romanized (Wylie-style) string into Tibetan script.
    """
    # Normalize input to lowercase to match the map keys
    roman_string = roman_string.lower()
    
    tibetan_output = ""
    i = 0
    while i < len(roman_string):
        # Handle space, which indicates the end of a syllable
        if roman_string[i] == ' ':
            tibetan_output += "་" # Appends the 'tsheg' character
            i += 1
            continue

        # Greedily find the longest matching key from our dictionary
        match_found = False
        for key in SORTED_KEYS:
            if roman_string.startswith(key, i):
                tibetan_output += TIBETAN_MAP[key]
                i += len(key)
                match_found = True
                break
        
        # If no key was matched, skip the character and move on
        if not match_found:
            i += 1
            
    return tibetan_output

def clear_text():
    """Returns empty strings to clear the text boxes."""
    return "", ""

# --- Gradio Interface ---
with gr.Blocks(theme=gr.themes.Soft()) as demo:
    gr.Markdown(
        """
        #  Tibetan Transliteration Tool 🏔️
        Type Romanized Tibetan (Wylie style) in the box below and see the Tibetan script appear in real-time.
        - Use a **space** to mark the end of a syllable (it will add a `་` tsheg).
        - Examples: `bod`, `sangs rgyas`, `bkra shis bde legs`
        """
    )
    
    with gr.Row():
        roman_input = gr.Textbox(
            label="Roman Input (Wylie)",
            placeholder="Type here, e.g., 'om ma ni pad me hung'",
            lines=4
        )
        tibetan_output = gr.Textbox(
            label="Tibetan Script Output",
            interactive=False,
            lines=4,
            rtl=True # Right-to-Left text direction for Tibetan
        )

    clear_button = gr.Button("Clear")

    # Event Handlers
    roman_input.change(
        fn=transliterate,
        inputs=roman_input,
        outputs=tibetan_output
    )
    
    clear_button.click(
        fn=clear_text,
        inputs=None,
        outputs=[roman_input, tibetan_output]
    )
    
    gr.Markdown("---")
    gr.Examples(
        examples=[
            "sangs rgyas",
            "bod skad",
            "rdo rje",
            "karma",
            "bkra shis bde legs"
        ],
        inputs=roman_input
    )

if __name__ == "__main__":
    demo.launch()
