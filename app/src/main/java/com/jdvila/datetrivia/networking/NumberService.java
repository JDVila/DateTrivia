package com.jdvila.datetrivia.networking;

import com.jdvila.datetrivia.model.NumberDate;
import com.jdvila.datetrivia.model.NumberYear;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NumberService {

    @GET("{year}/year?json")
    Call<NumberYear> getYear(@Path("year") String year);

    @GET("{month}/{day}?json")
    Call<NumberDate> getBirthday(@Path("month") String month, @Path("day") String day);

    @GET("random/{year}?json")
    Call<NumberYear> getSurprise(@Path("year") String year);
}
