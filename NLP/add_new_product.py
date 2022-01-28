# 1. check duplicates
# 2. add

import sys
import os
from jamo import h2j, j2hcj

def get_js_TF(sample):
  sample_list = list(sample)
  last_word = sample_list[-1]
  last_word_jamo_list = list(j2hcj(h2j(last_word)))
  last_jamo = last_word_jamo_list[-1]

  jongsung_TF = "T"

  if last_jamo in ['ㅏ', 'ㅑ', 'ㅓ', 'ㅕ', 'ㅗ', 'ㅛ', 'ㅜ', 'ㅠ', 'ㅡ', 'ㅣ', 'ㅘ', 'ㅚ', 'ㅙ', 'ㅝ', 'ㅞ', 'ㅢ', 'ㅐ,ㅔ', 'ㅟ', 'ㅖ', 'ㅒ']: 
    jongsung_TF = "F" 
 
  return jongsung_TF


with open('/var/www/y-orders/mecab-ko-dic-2.1.1-20180720/user-dic/orange.csv', 'r')as f:
    file_data = f.readlines()

# check and omit duplicates
with open('/var/www/y-orders/mecab-ko-dic-2.1.1-20180720/user-dic/orange.csv', 'w') as cf:
    seen = set()
    for line in file_data:
        if line in seen: continue

        seen.add(line)
        cf.write(line)

# add a new word
word = sys.argv[1]

jongsung_TF = get_js_TF(word)

line = '{},,,,NNP,*,{},{},*,*,*,*,*\n'.format(word, jongsung_TF, word)

file_data.append(line)


cmd1 = 'bash /var/www/y-orders/mecab-ko-dic-2.1.1-20180720/tools/add-userdic.sh'
cmd2 = '/var/www/y-orders/mecab-ko-dic-2.1.1-20180720 && sudo make clean'
cmd3 = '/var/www/y-orders/mecab-ko-dic-2.1.1-20180720 && sudo make install'

os.system(cmd1)
os.system(cmd2)
os.system(cmd3)

# change the weigh of the word
#with open('/home/ubuntu/mecab-ko-dic-2.1.1-20180720/user-orange.csv', 'r') as rf:
#    items = []
#    orange_data = rf.readlines()
#    for orange in orange_data:
#        items.append(orange.split(','))

#    for item in items:
#        item[3] = '0'



#with open('/home/ubuntu/mecab-ko-dic-2.1.1-20180720/user-orange.csv', 'w') as wf:
#    for item in items:
#        new_line = '{},{},{},{},NNP,*,{},{},*,*,*,*,*\n'.format(
#            item[0], item[1], item[2], item[3], item[6], item[7])

#        wf.write(new_line)

#with open('/home/ubuntu/mecab-ko-dic-2.1.1-20180720/user-orange.csv', 'r') as of:
#    o_data = of.readlines()

for f in file_data:
    print(f)
#print(o_data)
