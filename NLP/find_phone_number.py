# 전화번호를 찾아내는 함수

import re
import sys

def find_phone_num(text):
    number = ''
    if len(text) != 0:
        number_pattern = "010[-|\s|~|)]*[\d]{3,4}[-|\s]*[\d]{4}"
        try:
            s = re.search(number_pattern, text)
            if s != None:
                number = s.group(0)
                return number
        except AttributeError:
            number = ''

    return number
