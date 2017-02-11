package com.ptlearnpoint.www.problem_1_piashsarker;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by pt on 11/13/16.
 */

public interface ApiService {



    @POST("updateDeviceInfo")
    Call <Response> postPhoneInfo(@Query("project_name") String project_name,@Query("project_version_name") String projectVersion,@Query("project_version_code") String project_version_code,@Query("ip_address") String ip_address,@Query("device_imei") String device_imei,@Query("device_android_id") String device_android_id,@Query("country_iso") String country_iso,@Query("device_manufacturer") String device_manufacturer,@Query("device_brand") String device_brand,@Query("device_model") String device_model,@Query("api_key") String api_key);

}
