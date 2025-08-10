import gradio as gr

#keys sorted by length descending in the processing function
# to ensure the longest possible match is found first
TIBETAN_MAP = {

     # Consonants + vowels
    "ka": "ཀ",   "kha": "ཁ",   "gha": "ག",   "nga": "ང",
    "ca": "ཅ",   "cha": "ཆ",   "ja": "ཇ",   "jha": "ཇ",   "nya": "ཉ",
    "ta": "ཏ",   "tha": "ཐ",   "dha": "ད",   "na": "ན",
    "pa": "པ",   "pha": "ཕ",   "ba": "བ",    "wa": "བ",   "bha": "བ",   "ma": "མ",
    "tza": "ཙ",  "tzsa": "ཙ",  "tsza": "ཙ",  "tssa": "ཙ",  "tsa": "ཚ",   "za": "ཛ",   "wwa": "ཝ", "waa": "ཝ",
    "zha": "ཞ",  "sza": "ཟ",   "ya": "ཡ",   "ra": "ར",
    "la": "ལ",   "sha": "ཤ",   "sa": "ས",
    "ha": "ཧ",   "a": "ཨ",

    # syllable-final(ending) ('jejug chuu')
    "g": "ག",    "ng": "ང",    "n": "ན",    "b": "བ",
    "aa": "འ",   "m": "མ",     "l": "ལ",    "r": "ར",
    "s": "ས",    "p": "པ",     "k": "ག",    "dh": "ད",
    "e": "ད",    "d": "ད",

    #vowel diacritics(applied to consonants)
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
    "dhi": "དི",  "dhu": "དུ",  "dhe": "དེ",  "dho": "དོ",
    "ni": "ནི",   "nu": "ནུ",   "ne": "ནེ",   "no": "ནོ",
    "pi": "པི",   "pu": "པུ",   "pe": "པེ",   "po": "པོ",
    "phi": "ཕི",  "phu": "ཕུ",  "phe": "ཕེ",  "pho": "ཕོ",
    "bhi": "བི",  "bhu": "བུ",  "bhe": "བེ",  "bho": "བོ",
    "mi": "མི",   "mu": "མུ",   "me": "མེ",   "mo": "མོ",
    
    "tzi": "ཙི",  "tzu": "ཙུ",  "tze": "ཙེ",  "tzo": "ཙོ",
    "tssi": "ཙི", "tssu": "ཙུ", "tsse": "ཙེ", "tsso": "ཙོ",
    "tzsi": "ཙི", "tzsu": "ཙུ", "tzse": "ཙེ", "tzso": "ཙོ",
    "tszi": "ཙི", "tszu": "ཙུ", "tsze": "ཙེ", "tszo": "ཙོ",
    
    "tsi": "ཚི",  "tsu": "ཚུ",  "tse": "ཚེ",  "tso": "ཚོ",
    "zi": "ཛི",  "zu": "ཛུ",   "ze": "ཛེ",   "zo": "ཛོ",
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

 #subjoined "ra" ('ratak')
    "tra" : "ཀྲ", "tta": "ཁྲ", "tda": "ཁྲ", "da": "གྲ", "dra" : "དྲ", "ssa": "སྲ", "sra" :"སྲ", "nra" : "ནྲ", 
    ### IMPRT TO SUGGESTIONS ###    "ta": པྲ  ཕྲ བྲ ཏྲ 
          "thra" : "ཐྲ", "hra": "ཧྲ",
	"tri" : "ཀྲི", "tru": "ཀྲུ", "tre": "ཀྲེ", "tro": "ཀྲོ",
	"tdri" : "ཁྲི", "tdru" : "ཁྲུ", "tdre" : "ཁྲེ", "tdro" : "ཁྲོ",
	"di":"གྲི", "du":"གྲུ",  "de": "གྲེ", "do": "གྲོ",
	"dri":"དྲི", "dru":"དྲུ", "dre":"དྲེ", "dro":"དྲོ",  	"ddi":"དྲི", "ddu":"དྲུ", "dde":"དྲེ", "ddo":"དྲོ",
	
	"ssi" : "སྲི", "ssu": "སྲུ", "sse" : "སྲེ", "sso" :"སྲོ",
	#ནི ནྲུ ནྲེ ནྲོ
######SUGEESTi   :: :: པྲི པྲ པྲེ པྲ
	#### SUGGESTIONS :::: : : ཕྲི ཕྲུ ཕྲེ ཕྲོ
                    #####3   བྲི བྲུ བྲེ བྲོ
 ###########3ཏྲི ཏྲ ཏྲེ ཏྲ
###########ཐྲི ཐྲུ ཐྲེ ཐྲོ
	"hri":"ཧྲི", "hru" : "ཧྲུ", "hre": "ཧྲེ", "hro": "ཧྲོ",    
	
    #Subjoined "la" (latak)
         #ཀླཀླུ ཀླུ ཀླེ ཀློ
	#གླ གླི གླུ གླེ གློ
	#བླ བླི བླུ བླེ བློ
	#རླ རླི རླུ རླེ རློ
	#ཟླ ཟླི ཟླུ ཟླེ ཟློ
	#སླ སླི སླུ སླེ སློ	
	
	

    # Subjoined 'ya' ('yatak')
    "kya": "ཀྱ",  "kyi": "ཀྱི",  "kyu": "ཀྱུ",  "kye": "ཀྱེ",  "kyo": "ཀྱོ",
    "khya": "ཁྱ", "khyi": "ཁྱི",  "khyu": "ཁྱུ",  "khye": "ཁྱེ",  "khy": "ཁྱི",
    "gya": "གྱ",  "gyi": "གྱི",  "ghyi": "གྱི",  "gyu": "གྱུ",  "gye": "གྱེ",  "gyo": "གྱོ",
    "chhya": "པྱ", "chhyi": "པྱི", "chhyu": "པྱུ", "chhye": "པྱེ", "chhyo": "པྱོ",
    "chya": "ཕྱ",  "chyi": "ཕྱི",  "chyu": "ཕྱུ",  "chye": "ཕྱེ",  "chyo": "ཕྱོ",
    "jhya": "བྱ",  "jhyi": "བྱི",  "jhyu": "བྱུ",  "jhye": "བྱེ",  "jhyo": "བྱོ",
    "nyya": "མྱ", "nyyi": "མྱི", "nyyu": "མྱུ", "nyye": "མྱེ", "nyyo": "མྱོ",

##########################################################################################################################################################################################################################################
##########################################################################3NUMBERS########################################################################################################################################################
	"1": "༡",	"2": "༢",	"3": "༣",	"4": "༤",	"5": "༥",	"6": "༦",	"7": "༧",	"8": "༨",	"8": "༩",	"0": "༠",  
###########################################################################################################################################################################################################################################

}

# 5 ngon-jug prefixes and 3 stackers
NGON_JUG_PREFIXES = ["ག", "བ", "འ", "མ", "ད"]
STACKERS = ["ལ", "ར", "ས"]
SORTED_KEYS = sorted(TIBETAN_MAP.keys(), key=len, reverse=True)
MAX_SUGGESTIONS = 8 #increased to show more variations

#MODIFIED TRANSLITERATE FUNCTION
def transliterate(roman_string):
    """
    Translates a Roman (Wylie) string to Tibetan script.
    It now handles vowel diacritics correctly and supports complex stacks.
    """
    roman_string = roman_string.lower()
    tibetan_output = ""
    i = 0
    length = len(roman_string)

    while i < length:
        #handle spaces and punctuation first
        if roman_string[i] == ' ':
            tibetan_output += "་"  # tsek
            i += 1
            continue
        elif roman_string[i] == '.':
            tibetan_output += "།" # che
            i += 1
            continue

        #check for the longest possible key match from the current position
        found_match = False
        for key in SORTED_KEYS:
            if roman_string.startswith(key, i):
                tibetan_char = TIBETAN_MAP[key]
                #if its a vowel it modifies the previous character
                if key in "iuoe" and tibetan_output:
                    # check if last char is a consonant that can take a vowel
                    if 0x0F40 <= ord(tibetan_output[-1]) <= 0x0F6C:
                         tibetan_output += tibetan_char
                    else: #standalone vowel so add the 'ཨ' base
                         tibetan_output += TIBETAN_MAP['a'] + tibetan_char
                else:
                    tibetan_output += tibetan_char

                i += len(key)
                found_match = True
                break
        
        if not found_match:
            # no match found.. skip the character
            i += 1

    return tibetan_output


#SUGGESTION LOGIC
def get_suggestions(current_input):
    """
    Finds syllable suggestions based on the last Roman syllable being typed.
    """
    if not current_input or current_input.endswith(' '):
        return []

    last_word = current_input.split(' ')[-1].lower()
    if not last_word:
        return []

    #check if the typed word is in map that can be modified
    if last_word in TIBETAN_MAP:
        base_tibetan = TIBETAN_MAP[last_word]
        suggestions = []

        #generate Prefixed or ngonjug suggestions
        for prefix in NGON_JUG_PREFIXES:
            # Format: {Roman Trigger}:{Display Text}:{Tibetan Value}
            suggestions.append(f"{last_word}:Prefix {prefix}: {prefix}{base_tibetan}")

        #generate Stacked suggestions
        try:
            # Calculate the subjoined form of the base character
            subjoined = chr(0x0F90 + (ord(base_tibetan) - 0x0F40))
            for stacker in STACKERS:
                suggestions.append(f"{last_word}:Stack {stacker}: {stacker}{subjoined}")
        except (ValueError, TypeError):
            pass #this base character cannot be subjoined

        return suggestions

    return [] #return empty if not a base consonant

#main update function
def update_on_input(roman_string):
    tibetan_text = transliterate(roman_string)
    suggestions = get_suggestions(roman_string)
    
    button_updates = []
    for i in range(MAX_SUGGESTIONS):
        if i < len(suggestions):
            full_suggestion_data = suggestions[i]
            display_text = full_suggestion_data.split(':')[1]
            tibetan_value = full_suggestion_data.split(':')[2].strip()
            
            update = gr.Button(f"{display_text}: {tibetan_value}", value=full_suggestion_data, visible=True)
        else:
            update = gr.Button(visible=False)
        button_updates.append(update)

    return [tibetan_text] + button_updates

#BUTTON CLICK HANDLER
def update_input_with_suggestion(current_input, suggestion_data):
    """
    Replaces the last typed Roman syllable with the chosen Tibetan variation.
    """
    # suggestion_data is "ka:Prefix ག: གཀ"
    if not suggestion_data:
        return current_input

    roman_trigger = suggestion_data.split(':')[0]
    tibetan_value = suggestion_data.split(':')[2].strip()

    #replace the last occurrence of the roman trigger with the final tibetan value
    #this intentionally mixes scripts in the input box, which the main
    #transliterate function will now handle by re-composing the final string.
    parts = current_input.rsplit(roman_trigger, 1)
    new_input = tibetan_value.join(parts) + " "

    return new_input

#GRADIO INTERFACE#
with gr.Blocks(theme=gr.themes.Soft()) as demo:
    gr.Markdown(
        """
        # Tibetan Transliteration Tool
        """
    )

    with gr.Row():
        roman_input = gr.Textbox(
            label="Roman Input",
            placeholder="Type here...",
            lines=4
        )
        tibetan_output = gr.Textbox(
            label="Tibetan Script Output",
            interactive=False,
            lines=4,
            rtl=True
        )

    with gr.Row():
        suggestion_buttons = [gr.Button(visible=False) for _ in range(MAX_SUGGESTIONS)]

    clear_button = gr.Button("Clear")

    #Event Handlers
    roman_input.change(
        fn=update_on_input,
        inputs=roman_input,
        outputs=[tibetan_output] + suggestion_buttons
    )

    for btn in suggestion_buttons:
        btn.click(
            fn=update_input_with_suggestion,
            inputs=[roman_input, btn],
            outputs=roman_input
        ).then( #chain another event to update the output right away
            fn=update_on_input,
            inputs=roman_input,
            outputs=[tibetan_output] + suggestion_buttons
        )

    def clear_all():
        button_updates = [gr.Button(visible=False) for _ in range(MAX_SUGGESTIONS)]
        return ["", ""] + button_updates

    clear_button.click(
        fn=clear_all,
        inputs=None,
        outputs=[roman_input, tibetan_output] + suggestion_buttons
    )

if __name__ == "__main__":
    demo.launch()
