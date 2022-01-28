#번지, 동, 호, 층 추출 및 처리

def find_detailed_addy(address_display, text_list, query_list, end_ind):
    start = end_ind
    end = end_ind+4 if len<query_list) > end_ind+4 else len(query_list)

    while start < end:
        morph = mc.pos(text_list[start])

        if len(morph) > 1:
            if morph[0][1] == 'SN' and morph[1][0] == '단지':
                address_display += ' ' + text_list[start]
                query_list.remove(text_list[start])

            if morph[0][1] == 'SN' and morph[1][0] == '동':
            address_display += ' ' + text_list[start]
            query_list.remove(text_list[start])

            if morph[0][1] == 'SN' and morph[1][0] == '호':
            address_display += ' ' + text_list[start]
            query_list.remove(text_list[start])

            if morph[0][1] == 'SN' and (morph[1][0] == '층' or morph[1][0] == 'fl' or morph[1][0] == 'floor'):
            address_display += text_list[start]
            query_list.remove(text_list[start])

    start += 1

  return address_display, query_list
