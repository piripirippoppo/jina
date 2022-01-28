# 특수기호 
 # SC - 쉼표, 가운뎃점, 콜론, 빗금 EX) , · : /
 # SSO - 여는 괄호 EX) (, [
 # SSC - 닫는 괄호 EX) ), ]
 # SY - 붙임표(물결, 숨김, 빠짐), 기타기호(논리수학기호, 화폐기호)
 # SF - 마침표, 물음표, 느낌표
 # SE - 줄임표 EX) ...

import copy
import sys
from konlpy.tag import Mecab

def eliminate_sp_char(text):
    mc = Mecab()
    copy_text = copy.deepcopy(text).split()
    for i in range(len(copy_text)):
      morph = mc.pos(copy_text[i])

      for tuples in morph:
          tuples = list(tuples)
          if tuples[1] == 'SF' or tuples[1] == 'SE' or tuples[1] == 'SSO' or tuples[1] == 'SSC' or tuples[1] == 'SC' or tuples[1] == 'SY':
              if tuples[0] in copy_text[i]:
                  text = text.replace(tuples[0], '')
    
    return text
