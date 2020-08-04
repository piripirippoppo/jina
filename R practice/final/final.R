library(rvest)
library(magrittr)
library(tibble)
library(stringr)
library(ggplot2)
library(stats)

#STEP1
earthquakes <- read_html("https://www.kaggle.com/aerodinamicc/earthquakes-in-japan") %>%
  html_node("table.table.table-striped.table-responsive-md") %>%
  html_table() %>%
set_colnames(c("time", "latitude", "longitude", "depth", "mag", "magType", "nst", "gap", "dmin", "rms", "net", "id", "update", "place", "type", "horizontalError", "depthError", "magError", "magNst", "status")) %>%
  as_data_frame()

#STEP2
earthquakes <- earthquakes[c(1:14)] # chop "movie" column

earthquakes <- earthquakes %>% 
  type_convert(col_types = cols(datetime = col_datetime(format = "%F T"))) %>%
  unite("datetime", date, time, sep = " ", remove = FALSE)
  
weather <- weather %>%
  type_convert(col_types = cols(maximum_datetime = col_datetime(format = "%F %T"))) %>%
  unite("maximum_datetime", date, maximum_time, sep = " ", remove = FALSE)

weather <- weather %>%
  type_convert(col_types = cols(end_datetime = col_datetime(format = "%F %T"))) %>%
  unite("end_datetime", date, end_time, sep = " ")

weather <- weather %>% select(-c("start_time", "maximum_time"))
weather

#STEP3
#Column 1:  Starting date of the type II burst (yyyy/mm/dd format)
#Column 2:  Starting time (UT) of the type II burst (hh:mm format)
#Column 3:  Ending date of the type II burst (mm/dd format; year in Column 1 applies)
#Column 4:  Ending time of the Type II burst (hh:mm  format)
#Column 5:  Starting frequency of type II burst (kHz) [1]
#Column 6:  Ending frequency of type II burst (kHz) [1]
#Column 7:  Solar source location (Loc) of the associated eruption in heliographic coordinates [2]
#Column 8:  NOAA active region number (NOAA) [3]
#Column 9:  Soft X-ray flare importance (Imp)  [4]
#Column 10: Date of the associated CME (mm/dd format, Year in Column 1 applies) [5]
#Column 11: Time of the associated CME (hh:mm format)
#Column 12: Central position angle (CPA, degrees) for non-halo CMEs [6]
#Column 13: CME width in the sky plane (degrees) [7]
#Column 14: CME speed in the sky plane (km/s)
#Column 15: Link to the daily proton, height-time, X-ray (PHTX) plots [8]

nasa <- read_html("http://www.hcbravo.org/IntroDataSci/misc/waves_type2.html") %>%
  html_nodes('pre') %>% html_text() %>%
  str_split("\n") %>%
  purrr::as_vector() %>%
  str_subset("[0-9]{4}/[0-9]{2}/[0-9]{2}") %>%
  as_tibble() %>%
  separate(value, c("start_date", "start_time", "end_date",
                                    "end_time", "start_frequency", "end_frequency",
                                    "flare_location", "flare_region", "flare_classification",
                                    "cme_date", "cme_time", "cme_angle", "cme_width", "cme_speed"), extra = "drop", sep = "[ ]{1,}")

# STEP 4 
#1. Recode any missing entries as NA. Refer to the data description in 
#   http://cdaw.gsfc.nasa.gov/CME_list/radio/waves_type2_description.html (and above) 
#   to see how missing entries are encoded.

#2. The CPA column (cme_angle) contains angles in degrees for most rows, except for halo flares, 
#   which are coded as Halo. Create a new (logical) column that indicates if a row corresponds to 
#   a halo flare or not, and then replace Halo entries in the cme_angle column as NA.

#3. The width column indicates if the given value is a lower bound. Create a new (logical) column that
#   indicates if width is given as a lower bound, and remove any non-numeric part of the width column.

#4. Combine date and time columns for start, end and cme so they can be encoded as datetime objects.
nasa <- nasa %>%
  mutate(start_frequency = ifelse(start_frequency == "????", NA_character_, start_frequency),
         end_frequency = ifelse(end_frequency == "????", NA_character_, end_frequency),
         flare_location = ifelse(flare_location == "------", NA_character_, flare_location),
         flare_region = ifelse(flare_region == "-----", NA_character_, flare_region),
         flare_classification = ifelse(flare_classification == "----", NA_character_, flare_classification),
         cme_date = ifelse(cme_date == "--/--", NA_character_, cme_date),
         cme_time = ifelse(cme_time == "--:--", NA_character_, cme_time),
         cme_angle = ifelse(cme_angle == "----", NA_character_, cme_angle),
         cme_width = ifelse(cme_width == "---", NA_character_, cme_width),
         cme_width = ifelse(cme_width == "----", NA_character_, cme_width),
         cme_speed = ifelse(cme_speed == "----", NA_character_, cme_speed)) %>%
  mutate(halo = ifelse(cme_angle == "Halo", TRUE, FALSE),
         cme_angle = ifelse(cme_angle == "Halo", NA_character_, cme_angle)) %>%
  mutate(cme_width = ifelse(cme_width == "360h", 360, cme_width),
         width_limit = ifelse(grepl(">", cme_width), TRUE, FALSE)) %>%
  mutate(end_time = ifelse(end_time == "24:00", "23:59", end_time)) %>%
  mutate(end_date = paste(substring(start_date, 1,5), end_date, sep = "")) %>%
  mutate(cme_date = paste(substring(start_date, 1,5), cme_date, sep = "")) %>%
  unite("start_datetime", start_date, start_time, sep = " ") %>%
  unite("end_datetime", end_date, end_time, sep = " ") %>%
  unite("cme_datetime", cme_date, cme_time, sep = " ") %>%
  type_convert(col_types = cols(start_datetime = col_datetime(format = "%Y/%m/%d %H:%M"), 
                                max_datetime = col_datetime(format = "%Y/%m/%d %H:%M"),
                                end_datetime = col_datetime(format = "%Y/%m/%d %H:%M"))) %>%
  mutate(start_frequency = as.integer(start_frequency)) %>%
  mutate(end_frequency = as.integer(end_frequency)) %>%
  mutate(cme_datetime = ifelse(grepl("NA", cme_datetime), NA_character_, cme_datetime))


#PART 2
#STEP 1
nasa <- nasa %>%
  separate(flare_classification, c("flare_class", "flare_degree"), sep = 1, extra = "drop", remove = FALSE) %>%
  type_convert(col_types = cols(flare_degree = col_double(),
                                flare_region = col_integer()))

top50_unselected <- nasa %>%
  arrange(desc(flare_class), desc(flare_degree)) %>%
  slice(1:50) %>%
  tibble::rowid_to_column() %>%
  mutate(rank = rowid) %>%
  mutate(flare_classification = gsub("\\.$", ".0", flare_classification)) %>%
  separate(start_datetime, c("date", "start_time"), sep = " ", remove = FALSE) %>%
  separate(cme_datetime, c("date1", "maximum_time"), sep = " ", remove = FALSE) %>%
  separate(end_datetime, c("date2", "end_time"), sep = " ", remove = FALSE)

top50_tbl <- top50_unselected %>%
  select(c("rank", "flare_classification", "date", "flare_region", "start_time", "maximum_time", "end_time"))

#STEP 2
char_similarity <- function(v1, v2) {
  if (is.na(v1) || is.na(v2)) {
    return(0)
  }
  else { 
    ifelse(v1 == v2, 1, 0) 
  }
}

num_similarity <- function(v1, v2) {
  if (is.na(v1) || is.na(v2)) {
    return(0)
  }
  else {
    exp(-1*((v1 - v2)^2))
  }
}

date_similarity <- function(v1, v2) {
  if (is.na(v1) || is.na(v2)) {
    return(0)
  }
  else {
    exp(-1*(((as.numeric(v1) - as.numeric(v2))/3600)^2))
  }
}

solar_flares_unselected <- solar_flares %>%
  separate(flare_classification, c("flare_class", "flare_degree"), sep = 1, extra = "drop") %>%
  type_convert(col_types = cols(flare_degree = col_double()))

flare_similarity <- function(df1, df2) {
  score <- num_similarity(df1$flare_degree, df2$flare_degree) +
    date_similarity(df1$start_datetime, df2$start_datetime) +
    date_similarity(df1$end_datetime, df2$end_datetime) +
    num_similarity(df1$flare_region, df2$flare_region) +
    char_similarity(df1$flare_class, df2$flare_class)
  
  score
}

flare_match <- function(df1, df2) {
  matches <- c(0)
  
  for (i in seq(1, nrow(df1))) {
    max <- 0
    maxid <- 0
    
    for (j in seq(1, nrow(df2))) {
      ele <- flare_similarity(df1[i,], df2[j,])
      
      if (ele > max) {
        maxid <- j
        max <- ele
      }
    }
    
    matches[i] <- ifelse(max > 2, maxid, NA_character_)
  }
  
  as.integer(matches)
}

top50_tbl <- top50_tbl %>%
  mutate(best_match_index = flare_match(solar_flares_unselected, top50_unselected))

# STEP 3
nasa <- nasa %>%
  mutate(is_top50 = ifelse(is.na(flare_match(nasa_data, solar_flares_unselected)), FALSE, TRUE))

ggplot(nasa, aes(x=start_datetime, y=flare_degree, colour = is_top50)) +
  geom_point()

