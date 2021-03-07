package com.x.metiz;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HotelModel {

    @SerializedName("status_code")
    public String status_code;
    @SerializedName("data")
    public HotelData data;
    @SerializedName("server_timestamp")
    public String server_timestamp;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public HotelData getData() {
        return data;
    }

    public void setData(HotelData data) {
        this.data = data;
    }

    public String getServer_timestamp() {
        return server_timestamp;
    }

    public void setServer_timestamp(String server_timestamp) {
        this.server_timestamp = server_timestamp;
    }

    public class HotelData {

        public ArrayList<Hotel> hotels;
        public ArrayList<HotelFacility> hotel_facilities;
        public ArrayList<RoomFacility> room_facilities;

        public ArrayList<Hotel> getHotels() {
            return hotels;
        }

        public void setHotels(ArrayList<Hotel> hotels) {
            this.hotels = hotels;
        }

        public ArrayList<HotelFacility> getHotel_facilities() {
            return hotel_facilities;
        }

        public void setHotel_facilities(ArrayList<HotelFacility> hotel_facilities) {
            this.hotel_facilities = hotel_facilities;
        }

        public ArrayList<RoomFacility> getRoom_facilities() {
            return room_facilities;
        }

        public void setRoom_facilities(ArrayList<RoomFacility> room_facilities) {
            this.room_facilities = room_facilities;
        }

        public class Hotel {
            @SerializedName("place")
            public PlaceData place;
            public BookingCom booking_com;

            public PlaceData getPlace() {
                return place;
            }

            public void setPlace(PlaceData place) {
                this.place = place;
            }

            public BookingCom getBooking_com() {
                return booking_com;
            }

            public void setBooking_com(BookingCom booking_com) {
                this.booking_com = booking_com;
            }

            public class PlaceData {

                public String id;
                public String level;
                public String type;
                public double rating;
                public double rating_local;
                public String quadkey;
                @SerializedName("location")
                public LocationData location;
                public BoundingBox bounding_box;
                public String name;
                public String name_suffix;
                public String original_name;
                public String url;
                public Price price;
                public int duration;
                public String marker;
                public ArrayList<String> categories;
                public ArrayList<String> parent_ids;
                public String perex;
                public double customer_rating;
                public double star_rating;
                public double star_rating_unofficial;
                public String thumbnail_url;
                public Meta meta;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getRating() {
                    return rating;
                }

                public void setRating(double rating) {
                    this.rating = rating;
                }

                public double getRating_local() {
                    return rating_local;
                }

                public void setRating_local(double rating_local) {
                    this.rating_local = rating_local;
                }

                public String getQuadkey() {
                    return quadkey;
                }

                public void setQuadkey(String quadkey) {
                    this.quadkey = quadkey;
                }

                public LocationData getLocation() {
                    return location;
                }

                public void setLocation(LocationData location) {
                    this.location = location;
                }

                public BoundingBox getBounding_box() {
                    return bounding_box;
                }

                public void setBounding_box(BoundingBox bounding_box) {
                    this.bounding_box = bounding_box;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getName_suffix() {
                    return name_suffix;
                }

                public void setName_suffix(String name_suffix) {
                    this.name_suffix = name_suffix;
                }

                public String getOriginal_name() {
                    return original_name;
                }

                public void setOriginal_name(String original_name) {
                    this.original_name = original_name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public Price getPrice() {
                    return price;
                }

                public void setPrice(Price price) {
                    this.price = price;
                }

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public String getMarker() {
                    return marker;
                }

                public void setMarker(String marker) {
                    this.marker = marker;
                }

                public ArrayList<String> getCategories() {
                    return categories;
                }

                public void setCategories(ArrayList<String> categories) {
                    this.categories = categories;
                }

                public ArrayList<String> getParent_ids() {
                    return parent_ids;
                }

                public void setParent_ids(ArrayList<String> parent_ids) {
                    this.parent_ids = parent_ids;
                }

                public String getPerex() {
                    return perex;
                }

                public void setPerex(String perex) {
                    this.perex = perex;
                }

                public double getCustomer_rating() {
                    return customer_rating;
                }

                public void setCustomer_rating(double customer_rating) {
                    this.customer_rating = customer_rating;
                }

                public double getStar_rating() {
                    return star_rating;
                }

                public void setStar_rating(double star_rating) {
                    this.star_rating = star_rating;
                }

                public double getStar_rating_unofficial() {
                    return star_rating_unofficial;
                }

                public void setStar_rating_unofficial(double star_rating_unofficial) {
                    this.star_rating_unofficial = star_rating_unofficial;
                }

                public String getThumbnail_url() {
                    return thumbnail_url;
                }

                public void setThumbnail_url(String thumbnail_url) {
                    this.thumbnail_url = thumbnail_url;
                }

                public Meta getMeta() {
                    return meta;
                }

                public void setMeta(Meta meta) {
                    this.meta = meta;
                }

                public class LocationData {
                    public double lat;
                    public double lng ;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                    @NonNull
                    @Override
                    public String toString() {
                        return new Gson().toJson(this);
                    }
                }

                public class BoundingBox {
                    public double south;
                    public double west;
                    public double north;
                    public double east;

                    public double getSouth() {
                        return south;
                    }

                    public void setSouth(double south) {
                        this.south = south;
                    }

                    public double getWest() {
                        return west;
                    }

                    public void setWest(double west) {
                        this.west = west;
                    }

                    public double getNorth() {
                        return north;
                    }

                    public void setNorth(double north) {
                        this.north = north;
                    }

                    public double getEast() {
                        return east;
                    }

                    public void setEast(double east) {
                        this.east = east;
                    }
                    @NonNull
                    @Override
                    public String toString() {
                        return new Gson().toJson(this);
                    }
                }

                public class Price {

                    public double savings;
                    public double value;

                    public double getSavings() {
                        return savings;
                    }

                    public void setSavings(double savings) {
                        this.savings = savings;
                    }

                    public double getValue() {
                        return value;
                    }

                    public void setValue(double value) {
                        this.value = value;
                    }
                    @NonNull
                    @Override
                    public String toString() {
                        return new Gson().toJson(this);
                    }
                }


                public class Meta {
                    public int tier;
                    public String edited_at;
                    public boolean is_outdated;

                    public int getTier() {
                        return tier;
                    }

                    public void setTier(int tier) {
                        this.tier = tier;
                    }

                    public String getEdited_at() {
                        return edited_at;
                    }

                    public void setEdited_at(String edited_at) {
                        this.edited_at = edited_at;
                    }

                    public boolean isIs_outdated() {
                        return is_outdated;
                    }

                    public void setIs_outdated(boolean is_outdated) {
                        this.is_outdated = is_outdated;
                    }
                }
                @NonNull
                @Override
                public String toString() {
                    return new Gson().toJson(this);
                }
            }

            private class BookingCom {

                public String hotel_id;
                public double price;
                public int available_rooms_count;

                public String getHotel_id() {
                    return hotel_id;
                }

                public void setHotel_id(String hotel_id) {
                    this.hotel_id = hotel_id;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public int getAvailable_rooms_count() {
                    return available_rooms_count;
                }

                public void setAvailable_rooms_count(int available_rooms_count) {
                    this.available_rooms_count = available_rooms_count;
                }
            }
            @NonNull
            @Override
            public String toString() {
                return new Gson().toJson(this);
            }

        }

        public class HotelFacility {

            public String name;
            public String key;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
            @NonNull
            @Override
            public String toString() {
                return new Gson().toJson(this);
            }
        }

        public class RoomFacility {
            public String name;
            public String key;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
        @NonNull
        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
