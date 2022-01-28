# 아파트, 건물, 법인 등의 이름 추출

def find_build_name(building, query_list, end_ind):
  building_name = building.replace(' ', '')
  
  for query in query_list:
    if query.swapcase() in building_name or building_name in query.swapcase():
      return end_ind + 1
    
  return end_ind
