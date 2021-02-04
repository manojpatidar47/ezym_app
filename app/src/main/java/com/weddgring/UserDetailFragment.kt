package com.weddgring

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ezyloop.remot.ApiClass
import com.weddgring.databinding.FragmentUserDetailBinding
import com.weddgring.pojo.GetProfilePojo
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.weddgring.pojo.ConnectPojo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.roundToInt


class UserDetailFragment(
    var email: String?, var nestedScrollViewListner: NestedScrollView.OnScrollChangeListener
) : Fragment() {

    lateinit var fragmentUserDetailFragment: FragmentUserDetailBinding;
    lateinit var user_id: String
    lateinit var follower_id: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentUserDetailFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)

        setupdata()
        return fragmentUserDetailFragment.root
    }

    private fun setupdata() {
        user_id = SharedPreferences.readString(activity!!, SharedPreferences.userid, "").toString();

        fragmentUserDetailFragment.rlRequest.setOnClickListener {
            if (!follower_id.equals("")) {
                SendConnectionRequest()
            }else{
                Toast.makeText(activity, "Connection problem.Please try again later!", Toast.LENGTH_SHORT).show()

            }
            }
            fragmentUserDetailFragment.nested.setOnScrollChangeListener(nestedScrollViewListner)
            val call: Observable<GetProfilePojo>? = ApiClass.getApiServices(activity).getProfile(email)
            call?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<GetProfilePojo?> {
                    override fun onSubscribe(d: Disposable) { //  dialog.dismiss();
                        Log.e("responsePojo", "onSubscribe");
                        fragmentUserDetailFragment.shimmnerMain.startShimmerAnimation()
                    }

                    override fun onNext(getProfilepojo: GetProfilePojo) {
                        fragmentUserDetailFragment.shimmnerMain.stopShimmerAnimation()
                        Log.e("responsePojo", "responsePojo getProfilepojo" + getProfilepojo.toString());
                        try {
                            getProfilepojo.data?.let {
                                follower_id = it.id.toString()
                                fragmentUserDetailFragment.tvName.setText(it.fullname?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvEamil.setText(it.email.toString())
                                fragmentUserDetailFragment.tvReligion.setText(it.religion.toString())
                                fragmentUserDetailFragment.tvHeight.setText(it.height?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvCity.setText(it.city?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvDob.setText(it.dob?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvState.setText(it.state?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvCountry.setText(it.country?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvOccupation.setText(it.occupation?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvProfile.setText(it.profile?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvZodiac.setText(it.zodiac?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvInterest.setText(it.interests?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.caste.setText(it.caste?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.motherTongue.setText(it.mother_tongue?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.bloodGruop.setText(it.blood_group?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.familyStatus.setText(it.family_status?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.noofmember.setText(it.no_of_family_members?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvPhysicalStatus.setText(it.physical_status?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvComplexion.setText(it.complexion?.let { it.toString() }?:"Not Mentioned")

                                if (it.sex!!.equals("0")) {
                                    fragmentUserDetailFragment.tvGender.setText("Male")
                                } else {
                                    fragmentUserDetailFragment.tvGender.setText("Female")
                                }
                                try {
                                    if (it.drinking_habits?.let { it.toString() }?:"Not Mentioned".toFloat().roundToInt()==1) {
                                        fragmentUserDetailFragment.tvDrinking.setText("Yes")
                                    } else {
                                        fragmentUserDetailFragment.tvDrinking.setText("No")
                                    }
                                } catch (e: Exception) {
                                    fragmentUserDetailFragment.tvDrinking.setText("Not Mentioned")
                                }
                                if (it.eating_habits!!.equals("0")) {
                                    fragmentUserDetailFragment.tvEating.setText("Yes")
                                } else {
                                    fragmentUserDetailFragment.tvEating.setText("No")
                                }
                                try {
                                    if (it.smoking_habits?.let { it.toString() }?.toFloat()?.roundToInt()==1) {
                                          fragmentUserDetailFragment.tvSmoking.setText("Yes")
                                      } else {
                                          fragmentUserDetailFragment.tvSmoking.setText("No")
                                      }
                                } catch (e: Exception) {
                                    fragmentUserDetailFragment.tvSmoking.setText("Not Mentioned")
                                }
                                if (it.relationship_status!!.equals("0")) {
                                    fragmentUserDetailFragment.tvRelationship.setText("Married")
                                } else {
                                    fragmentUserDetailFragment.tvRelationship.setText("Unmarried")
                                }
                                if (it.sex!!.equals("0")) {
                                    fragmentUserDetailFragment.tvGender.setText("Male")
                                }else{
                                    fragmentUserDetailFragment.tvGender.setText("Female")
                                }
                                // radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> })
                                //  var drinkinghabit = fragmentUserDetailFragment.etDrinkinghabit.text?.let { it.toString() }?:"Not Mentioned"
                                // var smokinghabit = fragmentUserDetailFragment.etSmokinghabit.text?.let { it.toString() }?:"Not Mentioned"
                                fragmentUserDetailFragment.tvQualification.setText(it.highest_education?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvAnnual.setText(it.annual_income?.let { it.toString() }?:"Not Mentioned")
                                fragmentUserDetailFragment.tvHobies.setText(it.hobbies?.let { it.toString() }?:"Not Mentioned")
                            }
                            Glide.with(activity!!).load(getProfilepojo.file_url)
                                .placeholder(R.drawable.ring_icon)
                                .into(fragmentUserDetailFragment.ivProfileImg)
                            // refreshFragemnt()
                            // Log.e("userupdatepojo", registerPojo.getMsg()?.let { it.toString() }?:"Not Mentioned" + "nnn")

//registerPojoMutableLiveData.setValue(registerPojo);
                        } catch (e: Exception) {
                            Log.e("responsePojo", "responsePojo errr" +e.message);
                        }
                    }

                    override fun onError(e: Throwable) {
                        fragmentUserDetailFragment.shimmnerMain.stopShimmerAnimation()
                        //dialog!!.dismiss()
                        // refreshFragemnt()
                        if (e is HttpException) {
                            val responseBody = e.response().errorBody()
                            Log.e(
                                "onnext_login",
                                "$responseBody     responseBody"
                            )
                            /*  try {
                                  val jsonObject = JSONObject(responseBody.string())
                                  Log.e("onnext_login", jsonObject.getString("msg"))
                                  val loginPojo = LoginPojo()
                                  loginPojo.setSuccess(jsonObject.getString("success"))
                                  loginPojo.setMsg(jsonObject.getString("msg"))
                                  Toast.makeText(
                                      activity,
                                      "" + jsonObject.getString("msg"),
                                      Toast.LENGTH_SHORT
                                  ).show()
                              } catch (eq: java.lang.Exception) {
                                  val loginPojo = LoginPojo()
                                  loginPojo.setSuccess("0")
                                  loginPojo.setMsg("Server Error.Please try again!")
                                  Toast.makeText(
                                      activity,
                                      "Server Error.Please try again!",
                                      Toast.LENGTH_SHORT
                                  ).show()
                                  Log.e("onnext_login", eq.message + "    eq")
                              }*/
                        }
                        //   registerPojoMutableLiveData.setValue(loginPojo);
                        Log.e("userupdatepojo", "" + e.message)
                    }

                    override fun onComplete() {
                        // dialog!!.dismiss()
                        // refreshFragemnt()
                        Log.e("userupdatepojo", "complete")
                    }
                })
        }

        private fun SendConnectionRequest() {

            val call: Observable<ConnectPojo>? =
                ApiClass.getApiServices(activity).connect(user_id, follower_id)
            call?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ConnectPojo?> {
                    override fun onSubscribe(d: Disposable) { //  dialog.dismiss();
                        Log.e("responsePojo", "onSubscribe");
                        fragmentUserDetailFragment.shimmnerMain.startShimmerAnimation()
                    }

                    override fun onNext(connectionPojo: ConnectPojo) {
                        fragmentUserDetailFragment.shimmnerMain.stopShimmerAnimation()
                        Toast.makeText(activity, connectionPojo.message, Toast.LENGTH_SHORT).show()
//registerPojoMutableLiveData.setValue(registerPojo);
                    }

                    override fun onError(e: Throwable) {
                        fragmentUserDetailFragment.shimmnerMain.stopShimmerAnimation()
                        //dialog!!.dismiss()
                        // refreshFragemnt()
                        if (e is HttpException) {
                            val responseBody = e.response().errorBody()
                            Log.e(
                                "onnext_login",
                                "$responseBody     responseBody"
                            )
                            /*  try {
                                  val jsonObject = JSONObject(responseBody.string())
                                  Log.e("onnext_login", jsonObject.getString("msg"))
                                  val loginPojo = LoginPojo()
                                  loginPojo.setSuccess(jsonObject.getString("success"))
                                  loginPojo.setMsg(jsonObject.getString("msg"))
                                  Toast.makeText(
                                      activity,
                                      "" + jsonObject.getString("msg"),
                                      Toast.LENGTH_SHORT
                                  ).show()
                              } catch (eq: java.lang.Exception) {
                                  val loginPojo = LoginPojo()
                                  loginPojo.setSuccess("0")
                                  loginPojo.setMsg("Server Error.Please try again!")
                                  Toast.makeText(
                                      activity,
                                      "Server Error.Please try again!",
                                      Toast.LENGTH_SHORT
                                  ).show()
                                  Log.e("onnext_login", eq.message + "    eq")
                              }*/
                        }
                        //   registerPojoMutableLiveData.setValue(loginPojo);
                        Log.e("userupdatepojo", "" + e.message)
                    }

                    override fun onComplete() {
                        // dialog!!.dismiss()
                        // refreshFragemnt()
                        Log.e("userupdatepojo", "complete")
                    }
                })
        }

    }
