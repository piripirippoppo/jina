import re
import sys

def del_found_word(text, candidate_list):
    if candidate_list != None:
        if isinstance(candidate_list, str):
            candidate_list = candidate_list.split()

    for candidate in candidate_list:
        try:
            if candidate != None:
                c = re.search(candidate, text)
                if c != None:
                    text = text.replace(c.group(0), '')
        except AttributeError:
            text = text

    return text
