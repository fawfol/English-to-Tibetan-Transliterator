import gradio as gr

# Wylie Transliteration Map (cleaned and expanded from the Java version)
# Keys are sorted by length descending in the processing function
# to ensure the longest possible match is found first (e.g., 'khya' before 'khy' or 'k').
TIBETAN_MAP = {
    # Consonants + Vowels
    "ka": "ཀ", "ki": "ཀི", "ku": "ཀུ", "ke": "ཀེ", "ko": "ཀོ",
    "kha": "ཁ", "khi": "ཁི", "khu": "ཁུ", "khe": "ཁེ", "kho": "ཁོ",
    "ga": "ག", "gi": "གི", "gu": "གུ", "ge": "གེ", "go": "གོ",
    "nga": "ང", "ngi": "ངི", "ngu": "ངུ", "nge": "ངེ", "ngo": "ངོ",

    "ca": "ཅ", "ci": "ཅི", "cu": "ཅུ", "ce": "ཅེ", "co": "ཅོ",
    "cha": "ཆ", "chi": "ཆི", "chu": "ཆུ", "che": "ཆེ", "cho": "ཆོ",
    "ja": "ཇ", "ji": "ཇི", "ju": "ཇུ", "je": "ཇེ", "jo": "ཇོ",
    "nya": "ཉ", "nyi": "ཉི", "nyu": "ཉུ", "nye": "ཉེ", "nyo": "ཉོ",

    "ta": "ཏ", "ti": "ཏི", "tu": "ཏུ", "te": "ཏེ", "to": "ཏོ",
    "tha": "ཐ", "thi": "ཐི", "thu": "ཐུ", "the": "ཐེ", "tho": "ཐོ",
    "da": "ད", "di": "དི", "du": "དུ", "de": "དེ", "do": "དོ",
    "na": "ན", "ni": "ནི", "nu": "ནུ", "ne": "ནེ", "no": "ནོ",

    "pa": "པ", "pi": "པི", "pu": "པུ", "pe": "པེ", "po": "པོ",
    "pha": "ཕ", "phi": "ཕི", "phu": "ཕུ", "phe": "ཕེ", "pho": "ཕོ",
    "ba": "བ", "bi": "བི", "bu": "བུ", "be": "བེ", "bo": "བོ",
    "ma": "མ", "mi": "མི", "mu": "མུ", "me": "མེ", "mo": "མོ",

    "tsa": "ཙ", "tsi": "ཙི", "tsu": "ཙུ", "tse": "ཙེ", "tso": "ཙོ",
    "tsha": "ཚ", "tshi": "ཚི", "tshu": "ཚུ", "tshe": "ཚེ", "tsho": "ཚོ",
    "dza": "ཛ", "dzi": "ཛི", "dzu": "ཛུ", "dze": "ཛེ", "dzo": "ཛོ",
    "wa": "ཝ", "wi": "ཝི", "wu": "ཝུ", "we": "ཝེ", "wo": "ཝོ",

    "zha": "ཞ", "zhi": "ཞི", "zhu": "ཞུ", "zhe": "ཞེ", "zho": "ཞོ",
    "za": "ཟ", "zi": "ཟི", "zu": "ཟུ", "ze": "ཟེ", "zo": "ཟོ",
    "a": "ཨ", "'a": "འ", "ya": "ཡ", "yi": "ཡི", "yu": "ཡུ", "ye": "ཡེ", "yo": "ཡོ",
    "ra": "ར", "ri": "རི", "ru": "རུ", "re": "རེ", "ro": "རོ",
    "la": "ལ", "li": "ལི", "lu": "ལུ", "le": "ལེ", "lo": "ལོ",
    "sha": "ཤ", "shi": "ཤི", "shu": "ཤུ", "she": "ཤེ", "sho": "ཤོ",
    "sa": "ས", "si": "སི", "su": "སུ", "se": "སེ", "so": "སོ",
    "ha": "ཧ", "hi": "ཧི", "hu": "ཧུ", "he": "ཧེ", "ho": "ཧོ",
    
    # Vowels (Independent)
    "i": "ཨི", "u": "ཨུ", "e": "ཨེ", "o": "ཨོ",

    # Subjoined letters
    "kya": "ཀྱ", "khya": "ཁྱ", "gya": "གྱ",
    "kra": "ཀྲ", "khra": "ཁྲ", "gra": "གྲ",
    "pya": "པྱ", "phya": "ཕྱ", "bya": "བྱ", "mya": "མྱ",

    # Suffix letters (Jejug)
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

    roman_string = roman_string.lower()
    
    tibetan_output = ""
    i = 0
    while i < len(roman_string):
       
        if roman_string[i] == ' ':
            tibetan_output += "་"
            i += 1
            continue

        match_found = False
        for key in SORTED_KEYS:
            if roman_string.startswith(key, i):
                tibetan_output += TIBETAN_MAP[key]
                i += len(key)
                match_found = True
                break
        
        if not match_found:
            i += 1
            
    return tibetan_output

def clear_text():
    """Returns empty strings to clear the text boxes."""
    return "", ""

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
            rtl=True
        )

    clear_button = gr.Button("Clear")

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
