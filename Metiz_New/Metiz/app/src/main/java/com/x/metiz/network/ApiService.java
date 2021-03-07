package com.x.metiz.network;


import com.x.metiz.HotelModel;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("hotels/list/?")
    Observable<Response<HotelModel>> getHotelData(@Query("adults") String adult, @Query("check_in") String check_in, @Query("check_out") String check_out,
                                                  @Query("currency") String currency, @Query("limit") String limit,
                                                  @Query("bounds") String bounds);





}
