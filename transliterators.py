import gradio as gr

TIBETAN_MAP = {
    # Consonants + Vowels
    "ka": "ཀ", "ki": "ཀི", "ku": "ཀུ", "ke": "ཀེ", "ko": "ཀོ", "kha": "ཁ", "khi": "ཁི", "khu": "ཁུ", "khe": "ཁེ", "kho": "ཁོ", "ga": "ག", "gi": "གི", "gu": "གུ", "ge": "གེ", "go": "གོ", "nga": "ང", "ngi": "ངི", "ngu": "ངུ", "nge": "ངེ", "ngo": "ངོ",
    "ca": "ཅ", "ci": "ཅི", "cu": "ཅུ", "ce": "ཅེ", "co": "ཅོ", "cha": "ཆ", "chi": "ཆི", "chu": "ཆུ", "che": "ཆེ", "cho": "ཆོ", "ja": "ཇ", "ji": "ཇི", "ju": "ཇུ", "je": "ཇེ", "jo": "ཇོ", "nya": "ཉ", "nyi": "ཉི", "nyu": "ཉུ", "nye": "ཉེ", "nyo": "ཉོ",
    "ta": "ཏ", "ti": "ཏི", "tu": "ཏུ", "te": "ཏེ", "to": "ཏོ", "tha": "ཐ", "thi": "ཐི", "thu": "ཐུ", "the": "ཐེ", "tho": "ཐོ", "da": "ད", "di": "དི", "du": "དུ", "de": "དེ", "do": "དོ", "na": "ན", "ni": "ནི", "nu": "ནུ", "ne": "ནེ", "no": "ནོ",
    "pa": "པ", "pi": "པི", "pu": "པུ", "pe": "པེ", "po": "པོ", "pha": "ཕ", "phi": "ཕི", "phu": "ཕུ", "phe": "ཕེ", "pho": "ཕོ", "ba": "བ", "bi": "བི", "bu": "བུ", "be": "བེ", "bo": "བོ", "ma": "མ", "mi": "མི", "mu": "མུ", "me": "མེ", "mo": "མོ",
    "tsa": "ཙ", "tsi": "ཙི", "tsu": "ཙུ", "tse": "ཙེ", "tso": "ཙོ", "tsha": "ཚ", "tshi": "ཚི", "tshu": "ཚུ", "tshe": "ཚེ", "tsho": "ཚོ", "dza": "ཛ", "dzi": "ཛི", "dzu": "ཛུ", "dze": "ཛེ", "dzo": "ཛོ", "wa": "ཝ", "wi": "ཝི", "wu": "ཝུ", "we": "ཝེ", "wo": "ཝོ",
    "zha": "ཞ", "zhi": "ཞི", "zhu": "ཞུ", "zhe": "ཞེ", "zho": "ཞོ", "za": "ཟ", "zi": "ཟི", "zu": "ཟུ", "ze": "ཟེ", "zo": "ཟོ", "a": "ཨ", "'a": "འ", "ya": "ཡ", "yi": "ཡི", "yu": "ཡུ", "ye": "ཡེ", "yo": "ཡོ",
    "ra": "ར", "ri": "རི", "ru": "རུ", "re": "རེ", "ro": "རོ", "la": "ལ", "li": "ལི", "lu": "ལུ", "le": "ལེ", "lo": "ལོ", "sha": "ཤ", "shi": "ཤི", "shu": "ཤུ", "she": "ཤེ", "sho": "ཤོ", "sa": "ས", "si": "སི", "su": "སུ", "se": "སེ", "so": "སོ", "ha": "ཧ", "hi": "ཧི", "hu": "ཧུ", "he": "ཧེ", "ho": "ཧོ",
    # Independent Vowels
    "i": "ཨི", "u": "ཨུ", "e": "ཨེ", "o": "ཨོ",
    # Subjoined letters
    "kya": "ཀྱ", "khya": "ཁྱ", "gya": "གྱ", "kra": "ཀྲ", "khra": "ཁྲ", "gra": "གྲ", "pya": "པྱ", "phya": "ཕྱ", "bya": "བྱ", "mya": "མྱ",
    # Suffix letters (Jejug) used for syllable endings.
    "g": "ག", "ng": "ང", "d": "ད", "n": "ན", "b": "བ", "m": "མ", "'": "འ", "r": "ར", "l": "ལ", "s": "ས", "k": "ཀ", "p":"པ",
}

ENDING_LETTERS = {"g", "ng", "d", "n", "b", "m", "'", "r", "l", "s", "k", "p"}
VOWELS = {"a", "i", "u", "e", "o"}

def process_character(state, char):
    """
    This function contains the core logic, processing one character at a time
    based on the current state, exactly like the Java loop.
    """
    # If waiting for the next character (like after 'n' was typed)
    if state['is_waiting']:
        state['is_waiting'] = False
        
        # Try to combine the waiting character with the new one
        potential_combo = state['current_stack'] + char
        
        if potential_combo in TIBETAN_MAP:
            # It needs a vowel. So we update the stack and keep waiting.
            if char == 'h' or char == 'y':
                 state['current_stack'] = potential_combo
                 state['is_waiting'] = True 
            else:
                state['tibetan_output'] += TIBETAN_MAP[potential_combo]
                state['current_stack'] = ""
        else:
            suffix = state['current_stack']
            if suffix in TIBETAN_MAP:
                state['tibetan_output'] += TIBETAN_MAP[suffix]
            
            # Start new stack with the current character
            state['current_stack'] = char
            if char in ENDING_LETTERS:
                 state['is_waiting'] = True
    else:
        state['current_stack'] += char
        
        # Check if the new stack is a complete syllable
        if state['current_stack'] in TIBETAN_MAP:
            # If so, we wait to see what comes next.
            if state['current_stack'] in ENDING_LETTERS or state['current_stack'].endswith(('h', 'y')):
                state['is_waiting'] = True
            # Otherwise, it's a full syllable (like 'ka'). Finalize and clear.
            else:
                state['tibetan_output'] += TIBETAN_MAP[state['current_stack']]
                state['current_stack'] = ""
                
    return state


def transliterate_stateful(text, state):
    # --- Detect what happened: typing, deleting, or clearing ---
    prev_text = state.get('prev_text', '')
    
    # 1. Text was cleared or a large chunk was deleted/pasted
    if abs(len(text) - len(prev_text)) > 1 or (text == "" and prev_text != ""):
        # The state machine can't handle this gracefully, so we reset everything.
        new_state = {'prev_text': text, 'tibetan_output': '', 'current_stack': '', 'is_waiting': False}
        temp_output = ""
        i = 0
        sorted_keys = sorted(TIBETAN_MAP.keys(), key=len, reverse=True)
        while i < len(text):
            if text[i] == ' ':
                temp_output += "་"
                i += 1
                continue
            found = False
            for key in sorted_keys:
                if text.startswith(key, i):
                    temp_output += TIBETAN_MAP[key]
                    i += len(key)
                    found = True
                    break
            if not found: i+=1
        new_state['tibetan_output'] = temp_output
        return temp_output, new_state
        
    # 2. Backspace was pressed
    elif len(text) < len(prev_text):
        # We effectively "replay" the whole input string to rebuild the state.
        state = {'prev_text': '', 'tibetan_output': '', 'current_stack': '', 'is_waiting': False}
        for char in text:
            state = process_character(state, char)
    
    # 3. A single character was typed
    else:
        new_char = text[-1]
        
        if new_char == ' ':
            # Space finalizes the current stack and adds a tsheg '་'
            if state['current_stack'] in TIBETAN_MAP:
                state['tibetan_output'] += TIBETAN_MAP[state['current_stack']]
            state['tibetan_output'] += "་"
            state['current_stack'] = ""
            state['is_waiting'] = False
        else:
            # Run the main logic loop for the new character
            state = process_character(state, new_char.lower())

    state['prev_text'] = text
    # The final display is the transliterated output plus any unresolved letters in the stack
    return state['tibetan_output'] + state['current_stack'], state

# --- Gradio Interface ---
with gr.Blocks(theme=gr.themes.Soft()) as demo:
    gr.Markdown(
        """
        # Tibetan Transliteration (Stateful Logic) - 
        This version mimics the original Java code's character-by-character logic. It maintains a state to decide what to do with each new keystroke.
        - A **space** finalizes the current syllable.
        - **Pasting or large deletions will reset the state.**
        """
    )

    # gr.State holds our "memory" between function calls.
    # It's a dictionary containing all the state variables from the Java code.
    app_state = gr.State({
        'prev_text': '',          # To detect backspace/paste
        'tibetan_output': '',     # The finalized Tibetan string
        'current_stack': '',      # The current pending characters (like 'currentInput')
        'is_waiting': False,      # The 'waitingForNextInput' flag
    })

    with gr.Row():
        roman_input = gr.Textbox(
            label="Roman Input (Wylie)",
            placeholder="Type here, e.g., 'sangs rgyas'",
            lines=4
        )
        tibetan_output = gr.Textbox(
            label="Tibetan Script Output",
            interactive=False,
            lines=4,
            rtl=True
        )

    # The 'change' event triggers our stateful function
    roman_input.change(
        fn=transliterate_stateful,
        inputs=[roman_input, app_state],
        outputs=[tibetan_output, app_state]
    )
    
if __name__ == "__main__":
    demo.launch()
