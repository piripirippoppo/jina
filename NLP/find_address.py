#!/usr/bin/env python3
from konlpy.tag import Mecab
import re
import copy
import pandas as pd
from jamo import h2j, j2hcj
import requests
import urllib.request
import sys
import json

mc = Mecab()

def delete_found_word(text, candidate_list):
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

# 특수기호 
# SC - 쉼표, 가운뎃점, 콜론, 빗금 EX) , · : /
# SSO - 여는 괄호 EX) (, [
# SSC - 닫는 괄호 EX) ), ]
# SY - 붙임표(물결, 숨김, 빠짐), 기타기호(논리수학기호, 화폐기호)
# SF - 마침표, 물음표, 느낌표
# SE - 줄임표 EX) ...
def eliminate_sp_char(text):
    copy_text = copy.deepcopy(text).split()
    for i in range(len(copy_text)):
        morph = mc.pos(copy_text[i])
        
        for tuples in morph:
            tuples = list(tuples)
            if tuples[1] == 'SF' or tuples[1] == 'SE' or tuples[1] == 'SSO' or tuples[1] == 'SSC' or tuples[1] == 'SC' or tuples[1] == 'SY':
                if tuples[1] == 'SSO' or tuples[1] == 'SSC':
                    text = text.replace(tuples[0], '')
            
    return text

def find_phone_number(text):
    ## 정규식으로 보낸 번호 ex) 010-1234-5678
    if len(text) != 0:
        number_pattern = "[(]*010[-|\s|~|)]*[\d]{3,4}[-|\s]*[\d]{4}[)]*"
        try:
            s = re.search(number_pattern, text)
            if s != None:
                number = s.group(0)
                number = eliminate_sp_char(number)
        except AttributeError:
            number = ''

    return number

#1. check if the product is in the system (user-dic)
#2. 품목찾기
def name_finder(word):
    with open('../y-orders/mecab-ko-dic-2.1.1-20180720/user-orange.csv', 'r') as f:
        product = f.readlines()

    name = []
    for line in product:
        name.append(line.partition(',')[0])

    for n in name:
        if re.search(r'\b' + n + r'\b', word):
            return True
        
    return False

# 수량찾기
def quantity_finder(word):
    quantity = ["box", "b", "상자", "박스", "빡스", "BOX", "B", "개"]
    for q in quantity:
        if re.search(r'\b' + q + r'\b', word):
            return True

    return False

# 크기찾기
def size_finder(word):
    size = ["kg", "k", "키로", "킬로", "키로그램", "킬로그램", "KG", "K", "Kg", "호"]
    for s in size:
        if re.search(r'\b' + s + r'\b', word):
            return True

    return False

# 상품찾기 (품목, 수량, 크기 tuple return)
def find_product(text):
    copy_text = copy.deepcopy(text).split()

    product = ''
    quantity = ''
    size = ''
    
    for word in copy_text:
        w = mc.pos(word)
        if w[0][1] == 'NNP' or w[0][1] == 'NNG':
            if name_finder(word) == True:
                product += word

        if len(w) > 1:
            if w[0][1] == 'SN' or w[0][1] == 'MM':
                if quantity_finder(w[1][0]) == True:
                    quantity += word
        
            
                if size_finder(w[1][0]) == True:
                    size += word

    return product, quantity, size

# 아파트, 건물, 법인 등의 이름 추출
def find_building_name(building, query_list):
    building_name = building.replace(' ', '')
    
    for query in query_list:
        try:
            query = query.upper()
            query = eliminate_sp_char(query)
            if re.search(building, query) or re.search(query, building):
                return query
        except AttributeError:
                return []
    
    return []

# 번지, 동, 호, 층 추출 및 처리
def find_detailed_address(detailed_address, query_list, end_ind):
    res = []
    start = end_ind
    end = end_ind+4 if len(query_list) > end_ind+4 else len(query_list)

    while start < end:
        morph = mc.pos(query_list[start])
      
        if len(morph) > 1:
            if morph[0][1] == 'SN' and morph[1][0] == '단지':
                detailed_address += ' ' + query_list[start]
                res.append(query_list[start])

            if morph[0][1] == 'SN' and morph[1][0] == '동':
                detailed_address += ' ' + query_list[start]
                res.append(query_list[start])

            if morph[0][1] == 'SN' and morph[1][0] == '호':
                detailed_address += ' ' + query_list[start]
                res.append(query_list[start])

            if morph[0][1] == 'SN' and (morph[1][0] == '층' or morph[1][0] == 'fl' or morph[1][0] == 'floor'):
                detailed_address += query_list[start]
                res.append(query_list[start])

        start += 1

        if start >= len(query_list):
            break

    return detailed_address, res


def find_start_point(query_list):
    i = 0
    doshi = pd.read_csv('address/doshi.csv')
    doshi_list = list(doshi['도시'])

    while i < len(query_list):
        for doshi in doshi_list:
            if re.search(doshi, query_list[i]):
                return i
        i += 1
        if i >= len(query_list) - 1:
            return 0
    return i

def find_pro_address(query_list):
    res = ''
    res_list = []
    i = 0
    stop = False
    start = 0
    end = 0

    while i < len(query_list):
        i = find_start_point(query_list)
        start = i

        while stop == False:
            if re.search('길$', query_list[i]) or re.search('로$', query_list[i]) or re.search('동$', query_list[i]) or re.search('리$', query_list[i]):
                res += query_list[i] + ' '
                
                if re.search('길$', query_list[i]):
                    if re.search('로$', query_list[i+1]):
                        i += 1
                        res += query_list[i] + ' '
                
                elif re.search('로$', query_list[i]):
                    if re.search('길$', query_list[i+1]):
                        i += 1
                        res += query_list[i] + ' '
                           
                i += 1
                if i < len(query_list):
                    res += query_list[i] + ' '

                end = i
                stop = True
                
            else:
                if i >= len(query_list):
                    stop = True   
                else:
                    res += query_list[i] + ' '
                i += 1

        end = start + len(res.split())
        return start, end, res

        i += 1
        if i >= len(query_list):
            return start, end, res

def find_address(copy_original):
    kakao_key = "a03b8b8a8db755db91369d2cc7719005"
    path = "https://dapi.kakao.com/v2/local/search/address.json?analyze_type=similar&"
    headers = {"Authorization": "KakaoAK {}".format(kakao_key)}

    query_list = copy.deepcopy(copy_original).split()

    zipcode = ''
    address_display = ''
    detailed_address = ''
    building = ''

    del_words_list = []
    building_candidate = []
    detail_candidate = []

    start_ind = -1
    end_ind = 0

    start_ind, end_ind, candidate = find_pro_address(query_list)
    del_words_list.append(candidate)

    params = {"query": "{}".format(candidate)}

    kakao_request = requests.get(path, params=params, headers=headers)
    
    meta = kakao_request.json()["meta"]
    documents = kakao_request.json()["documents"]

    if len(documents) != 0:
        doc = documents[0]
        road = doc['road_address']
        jibun = doc['address']

        if road != None and road['zone_no'] != '' and meta.get('total_count') == 1:
            if doc['address_type'] == 'ROAD_ADDR':
                address_display += road['address_name']
            else:
                address_display += jibun['address_name']

            building += road['building_name']
            
            zipcode = road['zone_no']
            address_display += ' '
            detailed_address += road['building_name']

            # 빌딩이름 찾아 번지 뒤에 붙이고, 원본 메시지에서 삭제
            if len(building) != 0:
                building_candidate = find_building_name(building, query_list)
            if isinstance(building_candidate, str) and len(building_candidate) > 0:
                del_words_list.append(building_candidate)

            # 상세주소 찾아 빌딩이름 뒤에 붙이고, 원본 메시지에서 삭제
            detailed_address, detail_candidate = find_detailed_address(detailed_address, query_list, end_ind)
            del_words_list += detail_candidate
                  
    return (zipcode, address_display, detailed_address), del_words_list


def find_person_name(name_candidates):
    name_candidate_list = name_candidates.split()
    with open('../y-orders/mecab-ko-dic-2.1.1-20180720/Person.csv', 'r') as nr:
        names = nr.readlines()
        
    name_list = []
    for name in names:
        n = name.split(',')[0]
        if len(n) < 5:
            name_list.append(n)

    for c in name_candidate_list:
        w = mc.pos(c)
        if w[0][1] == 'NNP':
            for n in name_list:
                if re.search(w[0][0], n):
                    return w[0][0]
    return ''

original_text = sys.argv[1]

copy_original = copy.deepcopy(original_text)

# 1. 전화번호 추출
phone_number = find_phone_number(copy_original)

# 1.1 원본 텍스트에서 전화번호 지우기
copy_original = delete_found_word(copy_original, phone_number)

# 2. 주소 추출
full_address, del_address_list = find_address(copy_original)

# 2.1 원본 텍스트에서 주소 지우기
copy_original = delete_found_word(copy_original, del_address_list)

# 3. 특수기호 없애기
copy_original = eliminate_sp_char(copy_original)

# 4. 상품 추출(품목, 수량, 크기)
product_tuple = find_product(copy_original)

# 3.1 원본 텍스트에서 상품 (품목, 수량, 크기) 지우기
copy_original = delete_found_word(copy_original, list(product_tuple))

# 4. 이름 찾기
name_list = find_person_name(copy_original)

# 4.1 원본 텍스트에서 이름 지우기
copy_original = delete_found_word(copy_original, name_list)

misc = copy_original.split()

dic = {}
dic['original_text'] = original_text
dic['name'] = name_list 
dic['number'] = phone_number
dic['zipcode'] = full_address[0]
dic['address'] = full_address[1]
dic['detailed_address'] = full_address[2]
dic['prod_name'] = product_tuple[0]
dic['prod_quantity'] = product_tuple[1]
dic['prod_size'] = product_tuple[2]
dic['misc'] = misc

json_res = json.dumps(dic, ensure_ascii=False)

print(json_res)
