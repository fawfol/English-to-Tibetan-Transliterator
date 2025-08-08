import gradio as gr

#keys sorted by length descending in the processing function
# to ensure the longest possible match is found first
TIBETAN_MAP = {
     # Consonants + vowels
    "ka": "ཀ",   "kha": "ཁ",   "gha": "ག",   "nga": "ང",
    "ca": "ཅ",   "cha": "ཆ",   "ja": "ཇ",   "jha": "ཇ",   "nya": "ཉ",
    "ta": "ཏ",   "tha": "ཐ",   "dha": "ད",   "na": "ན",
    "pa": "པ",   "pha": "ཕ",   "ba": "བ",    "wa": "བ",   "bha": "བ",   "ma": "མ",
    "tza": "ཙ",  "tzsa": "ཙ",  "tsza": "ཙ",  "tssa": "ཙ",  "tsa": "ཚ",   "za": "ཛ",   "wwa": "ཝ",
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
    "di": "དི",   "du": "དུ",   "de": "དེ",   "do": "དོ",
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
    "nyya": "མྱ", "nyyi": "མྱི", "nyyu": "མྱུ", "nyye": "མྱེ", "nyyo": "མྱོ",

############################################################################################################################################################################################################################################################################
##########################################################################3NUMBERS##########################################################################################################################################################################################
	"1": "༡",	"2": "༢",	"3": "༣",	"4": "༤",	"5": "༥",	"6": "༦",	"7": "༧",	"8": "༨",	"8": "༩",	"0": "༠",  
############################################################################################################################################################################################################################################################################
      ".": "།",
}

# NEW: Dictionary for full word suggestions
TIBETAN_WORD_DICTIONARY = {
    "sangs rgyas": "སངས་རྒྱས",
    "bod skad": "བོད་སྐད",
    "rdo rje": "རྡོ་རྗེ",
    "karma": "ཀརྨ",
    "bkra shis": "བཀྲ་ཤིས",
    "bde legs": "བདེ་ལེགས",
    "cho": "ཆོས",
    "dharma": "དྷརྨ",
    "thugs rje": "ཐུགས་རྗེ",
    "nying rje": "སྙིང་རྗེ་",
    "om": "ཨོཾ་",
    "ma ni": "མ་ཎི་",
    "pad me": "པདྨེ་",
    "hung": "ཧཱུྃ་",
    "de key" : "བདེ་སྐྱིད་"
}

SORTED_KEYS = sorted(TIBETAN_MAP.keys(), key=len, reverse=True)
MAX_SUGGESTIONS = 5 #max number of suggestions to show

def transliterate(roman_string):
    roman_string = roman_string.lower()
    tibetan_output = ""
    i = 0
    length = len(roman_string)

    while i < length:
        if roman_string[i] == ' ':
            tibetan_output += "་"  # Tseg syllable delimiter
            i += 1
            continue

        match_found = False
        for key in SORTED_KEYS:
            if roman_string.startswith(key, i):
                # Your special handling for single 'e'
                if key == 'e':
                    next_pos = i + len(key)
                    # If 'e' is **at the end of the input** OR
                    # **the next character is a space** (i.e., end of syllable)
                    if next_pos == length or (next_pos < length and roman_string[next_pos] == ' '):
                        # Transliterate 'e' as the special suffix letter dha
                        tibetan_output += "ད"
                        i += len(key)
                        match_found = True
                        break
                    else:
                        #otherwise transliterate as vowel diacritic or ignore standalone 'e'
                        #we can either skip or add TIBETAN_MAP.get('e', '') if mapped correctly,
                        #butt often 'e' alone inside word is part of combos like 'de'
                        #skip if no standalone mapping:
                        i += len(key)
                        match_found = True
                        break

                else:
                    #all other keys behave normally
                    tibetan_output += TIBETAN_MAP[key]
                    i += len(key)
                    match_found = True
                    break

        if not match_found:
            i += 1 #skip unknown characters

    return tibetan_output


#function to get word suggestions
def get_suggestions(current_input):
    """Finds word suggestions based on the last word being typed."""
    if not current_input or current_input.endswith(' '):
        return []
    
    #find the last word being typed
    last_word = current_input.split(' ')[-1].lower()
    if not last_word:
        return []
        
    #find matches in our word dictionary
    matches = [
        word for word in TIBETAN_WORD_DICTIONARY 
        if word.startswith(last_word)
    ]
    
    return matches[:MAX_SUGGESTIONS]

# update all outputs when input changes
def update_on_input(roman_string):
    """
    Called when the user types.
    Returns updates for the Tibetan text and all suggestion buttons.
    """
    # 1.Transliterate the text
    tibetan_text = transliterate(roman_string)
    
    # 2.Get suggestions for the current input
    suggestions = get_suggestions(roman_string)
    
    # 3.Create a list of updates for the suggestion buttons
    # we make buttons visible if there's a suggestion, and invisible if not
    button_updates = []
    for i in range(MAX_SUGGESTIONS):
        if i < len(suggestions):
            #create a visible button with the suggestion
            suggestion_text = f"{suggestions[i]} ({TIBETAN_WORD_DICTIONARY[suggestions[i]]})"
            update = gr.Button(suggestion_text, visible=True)
        else:
            # Create an invisible button
            update = gr.Button(visible=False)
        button_updates.append(update)
        
    # return order must match 'outputs' list in the event handler
    return [tibetan_text] + button_updates

# handle clicking a suggestion button
def update_input_with_suggestion(current_input, suggestion_clicked):
    """
    Replaces the last typed word with the full suggestion.
    """
    suggestion = suggestion_clicked.split(' ')[0]
    
    parts = current_input.split(' ')
    # Replace the last part with the full suggestion and add a space
    new_input = ' '.join(parts[:-1] + [suggestion]) + ' '
    return new_input

def clear_all():
    """Returns empty strings and invisible buttons to clear everything."""
    button_updates = [gr.Button(visible=False) for _ in range(MAX_SUGGESTIONS)]
    return ["", ""] + button_updates

#GRADIO INTERFACE
with gr.Blocks(theme=gr.themes.Soft()) as demo:
    gr.Markdown(
        """
        #  Tibetan Transliteration Tool 
        Suggestions for complete words will appear as you type.
        """
    )
    
    with gr.Row():
        roman_input = gr.Textbox(
            label="Roman Input",
            placeholder="Type here",
            lines=4
        )
        tibetan_output = gr.Textbox(
            label="Tibetan Script Output",
            interactive=False,
            lines=4,
            rtl=True 
        )
    
    #row for suggestion buttons
    with gr.Row() as suggestion_row:
        suggestion_buttons = []
        for i in range(MAX_SUGGESTIONS):
            btn = gr.Button(visible=False)
            suggestion_buttons.append(btn)

    clear_button = gr.Button("Clear")

    # # # # # # # #
    # When the user types, update the Tibetan text AND the suggestion buttons
    roman_input.change(
        fn=update_on_input,
        inputs=roman_input,
        outputs=[tibetan_output] + suggestion_buttons
    )
    
    #for each suggestion button, create a click event
    for btn in suggestion_buttons:
        btn.click(
            fn=update_input_with_suggestion,
            inputs=[roman_input, btn],
            outputs=roman_input #update the main input box
        )
    
    clear_button.click(
        fn=clear_all,
        inputs=None,
        outputs=[roman_input, tibetan_output] + suggestion_buttons
    )
    
    gr.Markdown("---")

if __name__ == "__main__":
    demo.launch()
