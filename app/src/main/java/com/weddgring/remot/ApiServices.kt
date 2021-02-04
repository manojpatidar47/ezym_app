package com.ezyloop.remot



import com.weddgring.pojo.*
import io.reactivex.Observable
import okhttp3.Connection
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


interface ApiServices {

/*
    @POST("Login/login")
    @FormUrlEncoded
    fun login(@Field("email") email: String?, @Field("password") password: String?): Observable<LoginPojo>?

    @POST("Login/register")
    @FormUrlEncoded
    fun register(
        @Field("name") name: String?, @Field("email") email: String?, @Field(
            "password") password: String?, @Field("mobile") mobile: String?,@Field("code") code: String?): Observable<SignupPojo>?

    @POST("Stocks/booklist")
    @FormUrlEncoded
    fun booklist(@Field("machine_no") machine_no: String?): Observable<ShowBooksPojo>?

    @POST("Login/getCustomer")
    @FormUrlEncoded
    fun getCustomer(@Field("id") id: String?): Observable<PofilePojo>?

    @GET("Settings/privacy")
    fun getprivacypolicy(): Observable<PrivacyPolicyPojo>?

    @GET("Settings/terms")
    fun getterms(): Observable<TermsandConditionPojo>?


    @GET("Settings/aboutus")
    fun aboutus(): Observable<AboutUsPojo>?

    @POST("Login/updateProfile")
    @FormUrlEncoded
    fun updateProfile(@Field("id") id: String?,@Field("name") name: String?,@Field("email") email: String?,@Field("mobile") mobile: String?,@Field("dob") dob: String?,@Field("unit_number") unit_number: String?,@Field("postal_code") postal_code: String?,@Field("profile_image") profile_image: String?): Observable<UpdatePofilePojo>?

    @GET("Faq/faq")
    fun faq(): Observable<FAQPojo>?

    @POST("Support/add_thread")
    @FormUrlEncoded
    fun addThread(@Field("customer_id") customer_id: String?,@Field("subject") subject: String?,@Field("description") description: String?): Observable<ResponsePojo>?

    @POST("Support/getThreads")
    @FormUrlEncoded
    fun getThreads(@Field("id") customer_id: String?): Observable<ShowTicketsPojo>?

    @POST("Support/add_comment")
    @FormUrlEncoded
    fun add_comment(@Field("customer_id") customer_id: String?,@Field("comment") comment: String?,@Field("inquiry_id") inquiry_id: String?): Observable<ResponsePojo>?

    @POST("Support/getThreadsList")
    @FormUrlEncoded
    fun getThreadsList(@Field("inquiry_id") inquiry_id: String?): Observable<ShowTreadsListPojo>?

    @POST("Settings/get_ads")
    @FormUrlEncoded
    fun get_ads(@Field("id") id: String?): Observable<AdsPojo>?

    @POST("Settings/membership")
    @FormUrlEncoded
    fun get_membership(@Field("id") id: String?): Observable<MembershipPojo>?

    @POST("Login/change_password")
    @FormUrlEncoded
    fun change_password(@Field("id") id: String?,@Field("email") email: String?,@Field("old_pass") old_pass: String?,@Field("new_pass") new_pass: String?,@Field("confirm_new_pass") confirm_new_pass: String?): Observable<ResponsePojo>?

    @POST("Subscription/payment_api")
    @FormUrlEncoded
    fun payment_api(@Field("customer_id") customer_id: String?,@Field("sub_id") sub_id: String?,@Field("total_credit") total_credit: String?,@Field("amt") amt: String?,@Field("month") month: String?,@Field("plan_name") plan_name: String?,@Field("payable_amount") payable_amount: String?): Observable<PayementApiPojo>


    @POST("Subscription/getmachinedetails")
    @FormUrlEncoded
    fun getmachinedetails(@Field("machine_no") id: String?): Observable<MachineDetailPojo>

    @POST("Subscription/credit_log")
    @FormUrlEncoded
    fun credit_log(@Field("customer_id") customer_id: String?,@Field("credit") credit: String?,@Field("credit_type") credit_type: String?,@Field("action") action: String?,@Field("payment_id") payment_id: String?,@Field("month") month: String?): Observable<ResponsePojo>


    @POST("Subscription/book_issue")
    @FormUrlEncoded
    fun book_issue(@Field("machine_no") id: String?,@Field("customer_id") customer_id: String?,@Field("row") row: String?,@Field("column") column: String?): Observable<BookIssuePojo>

    @POST("Settings/get_book_history")
    @FormUrlEncoded
    fun get_book_history(@Field("customer_id") id: String?): Observable<BookHistoryPojo>?

    @POST("Settings/get_book_return")
    @FormUrlEncoded
    fun get_book_return(@Field("id") id: String?): Observable<BookReturnPojo>?

    @POST("Login/create_otp")
    @FormUrlEncoded
    fun create_otp(@Field("mobile") id: String?): Observable<CreateOTPPojo>?

    @POST("Login/check_otp")
    @FormUrlEncoded
    fun check_otp(@Field("mobile") mobile: String?,@Field("otp") otp: String?,@Field("flag") flag: String?): Observable<ResponsePojo>?

    @POST("Login/forgot_password")
    @FormUrlEncoded
    fun forgot_password(@Field("mobile") mobile: String?,@Field("new_pass") new_pass: String?): Observable<ResponsePojo>?

    @POST("Login/fb_register")
    @FormUrlEncoded
    fun fb_register(@Field("email") email: String?,@Field("name") name: String?,@Field("mobile") mobile: String?,@Field("pic") pic: String?): Observable<LoginPojo>?


    @POST("Subscription/save_token")
    @FormUrlEncoded
    fun save_token(@Field("id") id: String?,@Field("token") token: String?): Observable<ResponsePojo>?

    @POST("Subscription/get_customer_coins")
    @FormUrlEncoded
    fun get_customer_coins(@Field("id") id: String?,@Field("plan_amt") plan_amt: String?): Observable<CoinsPojo>?

    @POST("Subscription/store_used_coins")
    @FormUrlEncoded
    fun store_used_coins(@Field("id") id: String?, @Field("usedcoins") usedcoins:String?, @Field("coin_amount") coinsamt: String?, @Field("fine") fine: String?, @Field("total_amount_payable") total_amount_payable: String?,@Field("finecharged") finecharged: String? ): Observable<ResponsePojo>?

    @POST("Subscription/store_coins")
    @FormUrlEncoded
    fun store_coins(@Field("id") id: String?,@Field("code") code: String?): Observable<ResponsePojo>

    @POST("Settings/get_app_share_count")
    @FormUrlEncoded
     fun get_app_share_count(@Field("id") id: String?): Observable<ReferalPojo>*/
/*
    *//*    @POST("userDetails")
    @FormUrlEncoded
    Observable<UserProfilePojo> getUserdata(@Header("Authorization") String token,@Field("user_id") String  uder_id );*//*
    @POST("userDetails")
    @FormUrlEncoded
    fun getUserdata(@Field("user_id") uder_id: String?): Observable<UserProfilePojo?>?

    @POST("forgetPassword")
    @FormUrlEncoded
    fun changePassword(
        @Header("Authorization") token: String?, @Field(
            "user_id"
        ) uder_id: String?, @Field("password") password: String?, @Field(
            "new_pass"
        ) new_pass: String?
    ): Observable<RegisterPojo?>?

    @POST("userDetailsUpdate")
    @FormUrlEncoded
    fun updateProfile(
        @Header("Authorization") token: String?, @Field(
            "user_id"
        ) uder_id: String?, @Field("profile_photo") profile_photo: String?, @Field(
            "firstname"
        ) firstname: String?, @Field("lastname") lastname: String?, @Field(
            "phone"
        ) phone: String?
    ): Observable<RegisterPojo?>?

    @POST("resetPassword")
    @FormUrlEncoded
    fun forgotPassword(@Field("email") email: String?): Observable<ForgotPasswordPojo?>?

    @POST("compareOtp")
    @FormUrlEncoded
    fun compareOtp(
        @Field("email") email: String?, @Field(
            "otp"
        ) otp: String?
    ): Observable<OTPPojo?>?

    @POST("reset")
    @FormUrlEncoded
    fun reset(
        @Field("email") email: String?, @Field("new_pass") new_pass: String?, @Field(
            "cpass"
        ) cpss: String?
    ): Observable<ResetPojo?>?

    *//*  @GET("/api/users/{id}")
    public Call<UserApiResponse> getUser(@Path("id") Long id);*//*
    @POST("updateCurrentStatus")
    fun updateCurrentStatus(
        @Query("user_id") user_id: String?, @Query(
            "fullname"
        ) fullname: String?, @Query("latitude") latitude: String?, @Query(
            "longitude"
        ) longitude: String?, @Query("current_status") current_status: String?, @Query(
            "current_comment"
        ) current_comment: String?, @Query("current_battery_percentage") current_battery_percentage: String?
    ): Observable<UpdateCurrentPOJO?>?

    @POST("createGroup")
    @FormUrlEncoded
    fun createGroup(
        @Field("group_name") group_name: String?, @Field(
            "created_by"
        ) created_by: String?
    ): Observable<OTPPojo?>?

    @POST("groupDetails")
    @FormUrlEncoded
    fun groupDetails(@Field("user_id") user_id: String?): Observable<GroupDetailPojo?>?

    @POST("addUserToGroup")
    @FormUrlEncoded
    fun addUserToGroup(
        @Field("user_id") user_id: String?, @Field(
            "group_code"
        ) group_code: String?, @Field("is_admin") is_admin: String?
    ): Observable<OTPPojo?>?

    @GET("getLatLong")
    fun getGroupUserData(@Query("group_code") group_code: String?): Observable<GroupUserPojo?>?

    @POST("removeFromGroup")
    @FormUrlEncoded
    fun leaveGroup(
        @Field("user_id") user_id: String?, @Field(
            "group_id"
        ) group_id: String?
    ): Observable<OTPPojo?>?

    @POST("userAdminStatus")
    @FormUrlEncoded
    fun viewAdminStatus(@Field("group_code") group_code: String?): Observable<ViewAdminStatusPojo?>?

    @POST("startRide")
    @FormUrlEncoded
    fun startRide(
        @Field("user_id") user_id: String?, @Field(
            "start_lat"
        ) start_lat: String?, @Field("start_long") start_long: String?, @Field(
            "ride_start_date"
        ) ride_start_date: String?
    ): Observable<StartRidePojo?>?

    @POST("addEventsDetails")
    @FormUrlEncoded
    fun addEventsDetails(
        @Field("user_id") user_id: String?, @Field(
            "ride_id"
        ) ride_id: String?, @Field("event_type_id") event_type_id: String?, @Field(
            "latitude"
        ) latitude: String?, @Field("longitude") longitude: String?, @Field(
            "comments"
        ) comments: String?, @Field("created_date") created_date: String?
    ): Observable<AddEventRidePojo?>?

    @POST("updateRide")
    @FormUrlEncoded
    fun updateRide(
        @Field("user_id") user_id: String?, @Field(
            "ride_id"
        ) ride_id: String?, @Field("end_lat") end_lat: String?, @Field(
            "end_long"
        ) end_long: String?, @Field("ride_end_date") ride_end_date: String?, @Field(
            "speed"
        ) speed: String?
    ): Observable<OTPPojo?>?

    @POST("userHistory")
    @FormUrlEncoded
    fun userHistory(@Field("user_id") user_id: String?): Observable<DriveReportPojo?>?

    @POST("drivingReport")
    @FormUrlEncoded
    fun drivingReport(@Field("user_id") user_id: String?): Observable<RideListPojo?>?

    @POST("rideDetails")
    @FormUrlEncoded
    fun rideDetails(
        @Field("user_id") user_id: String?, @Field(
            "ride_id"
        ) ride_id: String?
    ): Observable<RideDetailPojo?>?

    @POST("incompleteRide")
    @FormUrlEncoded
    fun incompleteRide(@Field("user_id") user_id: String?): Observable<InCompleteRidePojo?>?
*/
/*    @POST("setup-profile")
    @FormUrlEncoded
    fun userAccountDelete(@Field("id") id: String?, @Field("fullname") fullname: String?, @Field("email") email: String?, @Field("sex") sex: Int, @Field("description") description: String?, @Field("height") height: String?, @Field("relationship_status") relationship_status: Int, @Field("city") city: String?, @Field("state") state: String?, @Field("country") country: String?, @Field("occupation") occupation: String?, @Field("profile") profile: String?, @Field("zodiac") zodiac: Int, @Field("religion") religion: String?, @Field("caste") caste: String?, @Field("mother_tongue") mother_tongue: String?, @Field("blood_group") blood_group: Int, @Field("family_status") family_status: String?, @Field("no_of_family_members") no_of_family_members: Int, @Field("physical_status") physical_status: String?, @Field("complexion") complexion: String?, @Field("eating_habits") eating_habits: Int, @Field("drinking_habits") drinking_habits: Int, @Field("smoking_habits") smoking_habits: Int, @Field("highest_education") highest_education: String?, @Field("annual_income") annual_income: String?, @Field("hobbies") hobbies: String?, @Field("interests") interests: String?, @Field("profile_for_dp") profile_for_dp: String?): Observable<ResponsePojo?>*/
   // @Body RequestBody file

    @POST("setup-profile")
    fun userAccountDelete(@Body file:RequestBody): Observable<ResponseBody?>


    @GET("profile/{email}")
    fun getProfile(@Path("email") email: String?):Observable<GetProfilePojo>?

    @POST("user-login")
    @FormUrlEncoded
    fun login(@Field("email") email: String?, @Field("password") password: String?): Observable<GetProfilePojo>?

    @POST("user-list")
    @FormUrlEncoded
    fun UserList(@Field("email") email: String?): Observable<UserListPojo>?

    @POST("user-login")

    fun user_login(@Body file:RequestBody): Observable<GetProfilePojo>?

    @POST("user-signup")
    @FormUrlEncoded
    fun user_signup(@Field("fullname") fullname: String?,@Field("email") email: String?, @Field("password") password: String?): Observable<SignupPojo>?


    @POST("connect")
    @FormUrlEncoded
    fun connect(@Field("follower_id") following_id: String?, @Field("following_id") follower_id: String?): Observable<ConnectPojo>?


    @POST("connect-request")
    @FormUrlEncoded
    fun connect_request(@Field("id") id: String?): Observable<ConnectRequestPojo>?

    @POST("feedback")
    @FormUrlEncoded
    fun feedback(@Field("fullname") following_id: String?, @Field("email") email: String?, @Field("message") message: String?): Observable<FeedbackPojo>?

}