package com.weddgring

import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ezyloop.remot.ApiClass
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.skyfishjy.library.RippleBackground
import com.weddgring.custom.RecycleViewScollListner
import com.weddgring.databinding.FragmentEditNewBinding
import com.weddgring.pojo.GetProfilePojo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.*
import java.util.*
import kotlin.math.roundToInt


class EditFragment(
    var nestedScrollChangeListener: NestedScrollView.OnScrollChangeListener,
   var recycleViewScollListner: RecycleViewScollListner
) :
    Fragment() {
    lateinit var fragmentEditBinding: FragmentEditNewBinding
    //lateinit var profileViewModel: ProfileViewModel
    var userID: String? = null
    val REQUEST_IMAGE_CAPTURE: Int = 1;
    val REQUEST_STORAGE_PERMISSION: Int = 1;
    var mTempPhotoPath: String? = null;

    val FILE_PROVIDER_AUTHORITY: String = "com.weddgring.provider";
    val RESULT_GALLERY = 0
    private var encoded_image = ""
    private var token: kotlin.String? = ""
    private var user_id: kotlin.String? = ""
    private var dob: kotlin.String? = ""
    private var drinking_hab: kotlin.String? = "0"
    private var smoking_hab: kotlin.String? = "0"
    private var email: kotlin.String? = ""
    lateinit var sp_gender: Spinner
    //new 
    private var file_send: File? = null
    var inte: Int? = null
    private var exifObject: ExifInterface? = null
    private var dialog: Dialog? = null
    private var mBottomSheetBehaviour: BottomSheetBehavior<*>? = null
    val gender_ary = arrayOf("Gender", "Male", "Female", "Other")
    val blood_grup_ary = arrayOf("Blood Group", "A+", "A-", "B+", "B-", "o+", "o-", "AB+", "AB-")
    val zodiac_ary = arrayOf(
        "Zodiac",
        "Capricorn",
        "Aquarius",
        "Pisces",
        "Aries",
        "Taurus",
        "Gemini",
        "Cancer",
        "Leo",
        "Virgo",
        "Libra",
        "Scorpio",
        "Ophiuchus",
        "Sagittarius"
    )
    val eatinghbalit_ary = arrayOf(
        "Eating Habit",
        "Vegetarian",
        "Non Vegetarian",
        "Eggetarian",
        "Fishetarian",
        "Others"
    )
    val relationship_status_ary =
        arrayOf("Relationship Status", "Single", "Widowed", "Divorced", "Awaiting Divorced")
    var zodiac: Int = 0
    var bld_grp: Int = 0
    var gender: Int = 0
    var etng_hbt: Int = 0
    var rlshp_sts: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentEditBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_new, container, false)
        initUI()
        return fragmentEditBinding.root
    }

    private fun initUI() {
        //inte=10
        inte?.let {
            Log.e("inyrr", "if")
        } ?: run {
            Log.e("inyrr", "else")
        }

        var aa_gen = ArrayAdapter(activity!!, R.layout.spinner_item, gender_ary);
        aa_gen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        fragmentEditBinding.spGender.setAdapter(aa_gen);

        fragmentEditBinding.spGender.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    gender = position
                }
            }
        })

        var aa_zodc = ArrayAdapter(activity!!, R.layout.spinner_item, zodiac_ary);
        aa_zodc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        fragmentEditBinding.spZodiac.setAdapter(aa_zodc);

        fragmentEditBinding.spZodiac.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    zodiac = position
                }
            }
        })


        var aa_reltn = ArrayAdapter(activity!!, R.layout.spinner_item, relationship_status_ary);
        aa_reltn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        fragmentEditBinding.spRelationshipStatus.setAdapter(aa_reltn);

        fragmentEditBinding.spRelationshipStatus.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    rlshp_sts = position
                }
            }
        })

        var aa_bld = ArrayAdapter(activity!!, R.layout.spinner_item, blood_grup_ary);
        aa_bld.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        fragmentEditBinding.spBldgrp.setAdapter(aa_bld);

        fragmentEditBinding.spBldgrp.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    bld_grp = position
                }
            }
        })


        var aa_eang = ArrayAdapter(activity!!, R.layout.spinner_item, eatinghbalit_ary);
        aa_eang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        fragmentEditBinding.spEatingHabit.setAdapter(aa_eang);

        fragmentEditBinding.spEatingHabit.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    etng_hbt = position
                }
            }
        })
        fragmentEditBinding.rgDrinking.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.rb_drk_no -> drinking_hab = "0"
                    R.id.rb_drk_yes -> drinking_hab = "1"
                }
            }
        })
        fragmentEditBinding.rgSmoking.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.rb_sm_no -> smoking_hab = "0"
                    R.id.rb_sm_yes -> smoking_hab = "1"
                }
            }
        })
        /*CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {

            }
        }*/

        /*  userID = SharedPreferences.readString(activity!!, SharedPreferences.userid, "").toString()
          profileViewModel = ViewModelProviders.of(activity!!).get(ProfileViewModel::class.java)
          //   fragmentEditBinding.pbProgress.visibility=View.VISIBLE
          profileViewModel.mutableLiveData!!.observe(activity!!, object :
              Observer<PofilePojo> {
              override fun onChanged(t: PofilePojo?) {
                  //   fragmentEditBinding.pbProgress.visibility=View.GONE
                  setupdata(t)
              }
          })*/
        /*profileViewModel.mutableLiveData!!.observeForever {
            fragmentEditBinding.pbProgress.visibility=View.GONE
            setupdata(it)
        }*/
        //  setupdata(profileViewModel.mutableLiveData!!.value)
        /* fragmentEditBinding.rlChnagepassword.setOnClickListener(object : View.OnClickListener {
             override fun onClick(v: View?) {
                 loadFragment(ChangePasswordFragment(), "ChangePasswordFragment")
             }
         })*/
        user_id = SharedPreferences.readString(activity!!, SharedPreferences.userid, "");
        email = SharedPreferences.readString(activity!!, SharedPreferences.email, "");
        setupdata();

        fragmentEditBinding.nested.setOnScrollChangeListener(nestedScrollChangeListener)
        fragmentEditBinding.rlUpdate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //  fragmentEditBinding.pbProgress.visibility = View.VISIBLE
                var fullname = fragmentEditBinding.tvName.text.toString()
                var email = fragmentEditBinding.tvEamil.text.toString()
                var religion = fragmentEditBinding.tvReligion.text.toString()
                var height = fragmentEditBinding.tvHeight.text.toString()
                var city = fragmentEditBinding.tvCity.text.toString()
                var state = fragmentEditBinding.tvState.text.toString()
                var dob = fragmentEditBinding.tvDob.text.toString()
                var counrty = fragmentEditBinding.tvCountry.text.toString()
                var occupadtion = fragmentEditBinding.tvOccupation.text.toString()
                var profile = fragmentEditBinding.tvProfile.text.toString()
                var interst = fragmentEditBinding.tvInterest.text.toString()
                var caste = fragmentEditBinding.caste.text.toString()
                var mothertogue = fragmentEditBinding.motherTongue.text.toString()
                var familystatus = fragmentEditBinding.familyStatus.text.toString()
                var members = fragmentEditBinding.noofmember.text.toString()
                var physical = fragmentEditBinding.tvPhysicalStatus.text.toString()
                var complexion = fragmentEditBinding.tvComplexion.text.toString()
                var eathabit = etng_hbt

                // radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> })
                //  var drinkinghabit = fragmentEditBinding.etDrinkinghabit.text.toString()
                // var smokinghabit = fragmentEditBinding.etSmokinghabit.text.toString()
                var qualification = fragmentEditBinding.tvHeight.text.toString()
                var annual = fragmentEditBinding.tvAnnual.text.toString()
                var hobies = fragmentEditBinding.tvHobies.text.toString()
                showdialog();
                Log.e("fullname", fullname + " full name");
                Log.e("filename", file_send.toString() + " file send");
                doUpdate(
                    user_id,
                    fullname,
                    email,
                    dob,
                    gender,
                    "sa",
                    height,
                    rlshp_sts,
                    city,
                    state,
                    counrty,
                    occupadtion,
                    profile,
                    zodiac,
                    religion,
                    caste,
                    mothertogue,
                    bld_grp,
                    familystatus,
                    members.toFloat().roundToInt(),
                    physical,
                    complexion,
                    eathabit,
                    drinking_hab!!.toInt(),
                    qualification,
                    annual,
                    hobies,
                    interst,
                    "as",
                    smoking_hab!!.toInt(),
                    file_send
                );
            }
        })
        val cl_grops_members = fragmentEditBinding.clChoose
        mBottomSheetBehaviour = BottomSheetBehavior.from(cl_grops_members)
        mBottomSheetBehaviour?.setState(BottomSheetBehavior.STATE_COLLAPSED)

        fragmentEditBinding.ivProfileImg.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Dexter.withActivity(activity)
                    .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                            if (report.areAllPermissionsGranted()) { /* Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, RESULT_GALLERY);*/
                                // launchCamera()
                                if (mBottomSheetBehaviour?.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                                    mBottomSheetBehaviour?.setState(BottomSheetBehavior.STATE_COLLAPSED)
                                    recycleViewScollListner.showBottom(true)
                                } else {
                                    mBottomSheetBehaviour?.setState(BottomSheetBehavior.STATE_EXPANDED)
                                    recycleViewScollListner.showBottom(false)
                                }
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Permission Denied!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: List<PermissionRequest>,
                            token: PermissionToken
                        ) { // token.continuePermissionRequest();
                            token.continuePermissionRequest()
                            //   Toast.makeText(activity, "decliened", Toast.LENGTH_SHORT).show();
                            Toast.makeText(
                                activity,
                                "Please Accept Storage permission!",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent =
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts(
                                "package",
                                activity!!.packageName,
                                null
                            )
                            intent.data = uri
                            startActivity(intent)
                        }
                    }).check()
            }
        })

        fragmentEditBinding.takePhoto.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                launchCamera()
                mBottomSheetBehaviour?.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        })
        /* fragmentEditBinding.rlChnagepassword.setOnClickListener {
             SharedPreferences.writeString(activity!!, SharedPreferences.userid, "")
             startActivity(Intent(activity!!, LoginActivity::class.java))
             activity!!.finishAffinity()
         }*/

        fragmentEditBinding.tvDob.setOnClickListener {
            //Toast.makeText(activity!!,"clicked",Toast.LENGTH_SHORT).show()
            var cldr = Calendar.getInstance();
            var day = cldr.get(Calendar.DAY_OF_MONTH);
            var month = cldr.get(Calendar.MONTH);
            var year = cldr.get(Calendar.YEAR);
            // date picker dialog
            var picker = DatePickerDialog(activity!!, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    fragmentEditBinding.tvDob.setText("${year}-${(month + 1)}-${dayOfMonth}"); }

            }, year, month, day)
            picker.show();
        }

        fragmentEditBinding.chooseGalalry.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val i = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(
                    i,
                    RESULT_GALLERY
                )
                mBottomSheetBehaviour?.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        })
        fragmentEditBinding.closeDia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mBottomSheetBehaviour?.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        })
    }

    private fun setupdata() {
        /*CoroutineScope(Dispatchers.IO).let {
            showdialog()
        }*/
        fragmentEditBinding.shimmerMain.startShimmerAnimation()
        val call: Observable<GetProfilePojo>? =
            ApiClass.getApiServices(activity).getProfile(email)
        call?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<GetProfilePojo?> {
                override fun onSubscribe(d: Disposable) { //  dialog.dismiss();
                    Log.e("responsePojo", "onSubscribe");
                }

                override fun onNext(getProfilepojo: GetProfilePojo) {
                    Log.e("getProfilepojo", getProfilepojo.toString() + "    getProfilepojo");
                    fragmentEditBinding.shimmerMain.stopShimmerAnimation()
                    getProfilepojo.data?.let {
                        fragmentEditBinding.tvName.setText(it.fullname?.let { it.toString() } ?: "")
                        fragmentEditBinding.tvEamil.setText(it.email?.let { it.toString() } ?: "")
                        fragmentEditBinding.tvReligion.setText(it.religion?.let { it.let { it.toString() } })
                        fragmentEditBinding.tvHeight.setText(it.height?.let { it.let { it.toString() } })
                        fragmentEditBinding.tvDob.setText(it.dob?.let { it.let { it.toString() } }
                            ?: "")
                        fragmentEditBinding.tvCity.setText(it.city?.let { it.let { it.toString() } }
                            ?: "")
                        fragmentEditBinding.tvState.setText(it.state?.let { it.toString() } ?: "")
                        fragmentEditBinding.tvCountry.setText(it.country?.let { it.toString() }
                            ?: "")
                        fragmentEditBinding.tvOccupation.setText(it.occupation?.let { it.toString() }
                            ?: "")
                        fragmentEditBinding.tvProfile.setText(it.profile?.let { it.toString() }
                            ?: "")

                        fragmentEditBinding.tvInterest.setText(it.interests?.let { it.toString() }
                            ?: "")
                        fragmentEditBinding.caste.setText(it.caste?.let { it.toString() } ?: "")
                        fragmentEditBinding.motherTongue.setText(it.mother_tongue?.let { it.toString() }
                            ?: "")
                        fragmentEditBinding.familyStatus.setText(it.family_status?.let { it.toString() }
                            ?: "")
                        fragmentEditBinding.noofmember.setText(it.no_of_family_members?.let { it.toString() }
                            ?: "0")
                        fragmentEditBinding.tvPhysicalStatus.setText(it.physical_status?.let { it.toString() }
                            ?: "")
                        fragmentEditBinding.tvComplexion.setText(it.complexion?.let { it.toString() }
                            ?: "")
                        fragmentEditBinding.tvNameTitle.setText(it.fullname?.let { it.toString() }
                            ?: "")

                        // radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> })
                        //  var drinkinghabit = fragmentEditBinding.etDrinkinghabit.text.let { it.toString() }
                        // var smokinghabit = fragmentEditBinding.etSmokinghabit.text.let { it.toString() }
                        fragmentEditBinding.tvQualification.setText(it.highest_education?.let { it.toString() }
                            ?: "")
                        fragmentEditBinding.tvAnnual.setText(it.annual_income?.let { it.toString() }
                            ?: "")
                        fragmentEditBinding.tvHobies.setText(it.hobbies?.let { it.toString() }
                            ?: "")

                        Log.e(
                            "spinnderdata",
                            "{${it.eating_habits}  ${it.blood_group}  ${it.zodiac}  ${it.relationship_status}}"
                        )
                        Log.e(
                            "radiodata",
                            "{${it.smoking_habits?.let { it.toString() }?.toFloat()?.roundToInt() == 1}  ${it.drinking_habits} }"
                        )
                        try {
                            if (it.smoking_habits?.let { it.toString() }?.toFloat()?.roundToInt() == 1) {
                                fragmentEditBinding.rbSmYes.isChecked=true
                            } else {
                                fragmentEditBinding.rbSmNo.isChecked=true
                            }
                        } catch (e: Exception) {
                            fragmentEditBinding.rbSmNo.isChecked=true
                        }
                        try {
                            if (it.drinking_habits?.let { it.toString() }?.toFloat()?.roundToInt() == 1) {
                                fragmentEditBinding.rbDrkYes.isChecked=true
                            } else {
                                fragmentEditBinding.rbDrkNo.isChecked=true
                            }
                        } catch (e: Exception) {
                            fragmentEditBinding.rbDrkNo.isChecked=true
                        }
                        it.eating_habits?.let {

                            Log.e(
                                "spinnereating",
                                (it.toString()).toFloat().roundToInt().toString()
                            );

                            if (it is Float) {
                                Log.e("spinnereating", it.toString())
                            }

                            fragmentEditBinding.spEatingHabit.setSelection(it.toString().toFloat().roundToInt())
                        }
                        it.blood_group?.let {
                            fragmentEditBinding.spBldgrp.setSelection(it.toString().toFloat().roundToInt())
                        }

                        it.zodiac?.let {
                            fragmentEditBinding.spZodiac.setSelection(it.toString().toFloat().roundToInt())
                        }

                        it.relationship_status?.let {
                            fragmentEditBinding.spRelationshipStatus.setSelection(it.toString().toFloat().roundToInt())
                        }

                        it.sex?.let {
                            fragmentEditBinding.spGender.setSelection(it.toString().toFloat().roundToInt())
                        }

                    }
                    if (!getProfilepojo.file_url.equals("http://15.206.66.117/ezymp/public/uploads/dp")) {
                        Glide.with(activity!!).load(getProfilepojo.file_url)
                            .placeholder(R.drawable.ring_icon).into(fragmentEditBinding.ivProfileImg)
                        // refreshFragemnt()
                        // Log.e("userupdatepojo", registerPojo.getMsg().toString() + "nnn")
                        Log.e("responsePojo", "responsePojo " + getProfilepojo.toString());
                    }

//registerPojoMutableLiveData.setValue(registerPojo);
                }

                override fun onError(e: Throwable) {
                    //  dialog!!.dismiss()
                    // refreshFragemnt()
                    if (e is HttpException) {
                        val responseBody = e.response()
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
                    //   dialog!!.dismiss()
                    // refreshFragemnt()
                    Log.e("userupdatepojo", "complete")
                }
            })
    }

    /*private fun updatedata() {
        var customer: Customer? = Customer()
        customer?.customer_id = userID
        customer?.customer_name = fragmentEditBinding.txtFirstName.text.toString()
        customer?.email = fragmentEditBinding.txtEmail.text.toString()
        customer?.mobile = fragmentEditBinding.txtPhone.text.toString()
        customer?.dob = fragmentEditBinding.txtDateofbirth.text.toString()
        customer?.unit_number = fragmentEditBinding.txtCIvAddress.text.toString()
        customer?.postal_code = fragmentEditBinding.txtPostal.text.toString()
        customer?.profile_image = encoded_image

        profileViewModel.getMutableUpdatedlivedata(activity!!, customer!!).observe(this, object :
            Observer<PofilePojo> {
            override fun onChanged(t: PofilePojo?) {
                fragmentEditBinding.pbProgress.visibility = View.GONE

                setupdata(t)
            }
        })
    }*/

    /* private fun setupdata(t: PofilePojo?) {
         t?.getCustomer()?.let {
             fragmentEditBinding.tvName.setText(it?.let { it.customer_name })
             fragmentEditBinding.txtFirstName.setText(it?.let { it.customer_name })
             fragmentEditBinding.txtEmail.setText(it?.let { it.email })
             fragmentEditBinding.txtPhone.setText(it?.let { it.mobile })
             fragmentEditBinding.txtCIvAddress.setText(it?.let { it.unit_number })
             fragmentEditBinding.txtDateofbirth.setText(it?.let { it.dob })
             fragmentEditBinding.txtPostal.setText(it?.let { it.postal_code })

             Glide.with(this)
                 .load(it?.let { ApiClass.baseurl_profile_image + it.profile_image })
                 .placeholder(R.drawable.changeimage).into(fragmentEditBinding.ivProfileImg)
             *//* Glide.with(activity!!)
                 .load("https://www.qitstaging.com/iot/uploads/images/customers/1596173569.png")
                 .placeholder(R.drawable.changeimage).into(fragmentEditBinding.ivProfileImg)*//*
        }
    }
*/
    /*private fun loadFragment(
        fragment: Fragment,
        name: String
    ) { // load fragment
        val transaction =
            activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contain_home, fragment, name)

        transaction.addToBackStack(name)
        transaction.commit()
    }
*/

    private fun launchCamera() { // Create the capture image intent
        val takePictureIntent =
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) { // Create the temporary File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = BitmapUtils.createTempImageFile(activity!!)
                file_send = photoFile;
            } catch (ex: IOException) { // Error occurred while creating the File
                ex.printStackTrace()
            }
            // Continue only if the File was successfully created
            if (photoFile != null) { // Get the path of the temporary file
                mTempPhotoPath = photoFile.absolutePath
                Log.e("mTempPhotoPath", mTempPhotoPath + "   mTempPhotoPath¶")
                // Get the content URI for the image file
                val photoURI = FileProvider.getUriForFile(
                    activity!!,
                    FILE_PROVIDER_AUTHORITY,
                    photoFile
                )
                Log.e("mTempPhotoPath", "$photoURI   mTempPhotoPath¶")
                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                // Launch the camera activity
                startActivityForResult(
                    takePictureIntent,
                    REQUEST_IMAGE_CAPTURE
                )
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_GALLERY -> if (null != data) { //  imageUri = data.getData();
                Log.e("gallary_uri", data.data.toString() + "  chel")
                var path: String? = getPathFromUri(activity!!, data.data!!)

                val file = File(path)
                file_send = file
                var orientation = 0
                try {
                    exifObject = ExifInterface(path)
                    orientation = exifObject!!.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                Log.e("orentation", "$orientation ")
                val imageRotate: Bitmap? = rotateBitmap(getBitmap(data.data!!)!!, orientation)
                fragmentEditBinding.ivProfileImg.setImageBitmap(imageRotate)
                try {
                    Log.e("encoded_image", encodeImage(imageRotate!!))
                    encoded_image = encodeImage(imageRotate!!)!!
                } catch (e: Exception) {
                    Log.e("encoded_image", e.message)
                    e.printStackTrace()
                }
                //change "getPic()" for whatever you need to open the image file.
// Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
// original measurements
                val origWidth = imageRotate?.width
                val origHeight = imageRotate?.height
                val destWidth = 600 //or the width you need
                if (origWidth!! > destWidth) {
                    try { // picture is wider than we want it, we calculate its target height
                        val destHeight = origHeight?.div((origWidth / destWidth))
                        // we create an scaled bitmap so it reduces the image, not just trim it
                        val b2 =
                            Bitmap.createScaledBitmap(
                                imageRotate!!,
                                destWidth,
                                destHeight!!,
                                false
                            )
                        val outStream =
                            ByteArrayOutputStream()
                        // compress to the format you want, JPEG, PNG...
// 70 is the 0-100 quality percentage
                        b2.compress(Bitmap.CompressFormat.JPEG, 70, outStream)
                        // we save the file, at least until we have made use of it
                        val f = File(
                            Environment.getExternalStorageDirectory()
                                .toString() + File.separator + "upload.jpg"
                        )
                        f.createNewFile()
                        try {
                            Log.e("encoded_image_b2", encodeImage(b2))
                            encoded_image = encodeImage(b2!!)!!
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        //write the bytes in file
                        val fo = FileOutputStream(f)
                        fo.write(outStream.toByteArray())
                        //  int orientation = exifObject.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
//  Log.e("orentation",orientation+" ");
// Bitmap imageRotate = rotateBitmap(b2,orientation);
//  showdialog();
// doUpdate();
//doPhotoUpdate(f);
// remember close de FileOutput
                        fo.close()
                    } catch (e: IOException) { /*showdialog();
                            doUpdate();*/
                        e.printStackTrace()
                        // doPhotoUpdate(file);
                    }
                }
                //  doProfileUpdate(encoded_license_image);
//Do whatever that you desire here. or leave this blank
            }
            REQUEST_IMAGE_CAPTURE -> {
                val mResultsBitmap =
                    BitmapUtils.resamplePic(activity!!, mTempPhotoPath)
                fragmentEditBinding.ivProfileImg.setImageBitmap(mResultsBitmap)
                try {
                    Log.e("encoded_image", encodeImage(mResultsBitmap))
                    encoded_image = encodeImage(mResultsBitmap)!!
                } catch (e: Exception) {
                    Log.e("encoded_image", e.message)
                    e.printStackTrace()
                }
            }
            else -> {
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getPathFromUri(
        context: Context,
        uri: Uri
    ): String? {
        val isKitKat =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) { // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(
                uri.scheme,
                ignoreCase = true
            )
        ) { // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    fun rotateBitmap(
        bitmap: Bitmap,
        orientation: Int
    ): Bitmap? {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }
        return try {
            val bmRotated = Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
            bitmap.recycle()
            bmRotated
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            null
        }
    }

    private fun getBitmap(uri: Uri): Bitmap? {
        val imageStream: InputStream?
        var selectedImage: Bitmap? = null
        try {
            imageStream = activity!!.contentResolver.openInputStream(uri)
            selectedImage = BitmapFactory.decodeStream(imageStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return selectedImage
    }

    @Throws(java.lang.Exception::class)
    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun showProfileDailog() { // Toast.makeText(context, "showDialog", Toast.LENGTH_SHORT).show();
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.show_take_profile_option)
        val take_photo =
            dialog.findViewById<View>(R.id.take_photo) as TextView
        take_photo.setOnClickListener {
            /**/
            launchCamera()
            dialog.dismiss()
        }
        val choose_galalry =
            dialog.findViewById<View>(R.id.choose_galalry) as TextView
        choose_galalry.setOnClickListener {
            val i = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(
                i,
                RESULT_GALLERY
            )
            dialog.dismiss()
        }
        dialog.setCancelable(true)
        dialog.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    fun doUpdate(
        id: String?,
        fullname: String?,
        email: String?,
        dob: String?,
        sex: Int,
        description: String?,
        height: String?,
        relationshipstatus: Int,
        city: String?,
        state: String?,
        country: String?,
        occupation: String?,
        profile: String?,
        zodiac: Int,
        religion: String?,
        caste: String?,
        tongue: String?,
        bloodgrup: Int,
        familystats: String?,
        nooffamily: Int,
        physicalstatus: String?,
        complexion: String?,
        eatinghabits: Int,
        drinkinghabits: Int,
        highest_qual: String?,
        annual_income: String?,
        hobbies: String?,
        interest: String?,
        profilefordp: String?,
        smoking_habits: Int,
        fileSend: File?
    ) {

        /*    var filePaths = ArrayList<String>();
           filePaths.add("storage/emulated/0/DCIM/Camera/IMG_20170802_111432.jpg");
           filePaths.add("storage/emulated/0/Pictures/WeLoveChat/587c4178e4b0060e66732576_294204376.jpg");
           filePaths.add("storage/emulated/0/Pictures/WeLoveChat/594a2ea4e4b0d6df9153028d_265511791.jpg");
   for (i in 0 until filePaths.size) {
               var file = File(filePaths.get(i));
               builder.addFormDataPart(
                   "file[]",
                   file.getName(),
                   RequestBody.create(MediaType.parse("multipart/form-data"), file)
               );
           }*/


        var builder = MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);


        /*    id:10
            fullname:dvfd
            email:check
    //profile_for_dp:
    //email:
            sex:0
            dob:loading...
            description:loading...
            height:loading...
            relationship_status:0
            city:loading...
            state:loading...
            country:loading...
            occupation:loading...
            profile:loading...
            zodiac:1
            religion:loading...
            caste:loading...
            mother_tongue:loading...
            blood_group:1
            family_status:loading...
            no_of_family_members:5
            physical_status:loading...
            complexion:loading...
            eating_habits:0
            drinking_habits:0
            smoking_habits:0
            highest_education:loading...
            annual_income:loading...
            interests:loading...
            hobbies:cricket
            profile_for:check
    */





        builder.addFormDataPart("id", user_id);
        builder.addFormDataPart("fullname", fullname);
        builder.addFormDataPart("email", email);
        builder.addFormDataPart("dob", dob);
        builder.addFormDataPart("sex", sex.toString());
        builder.addFormDataPart("description", description);
        builder.addFormDataPart("height", height);
        builder.addFormDataPart("relationship_status", relationshipstatus.toString());
        builder.addFormDataPart("city", city);
        builder.addFormDataPart("state", state);
        builder.addFormDataPart("country", country);
        builder.addFormDataPart("occupation", occupation);
        builder.addFormDataPart("profile", profile);
        builder.addFormDataPart("zodiac", zodiac.toString());
        builder.addFormDataPart("religion", religion);
        builder.addFormDataPart("caste", caste);
        builder.addFormDataPart("mother_tongue", tongue);
        builder.addFormDataPart("blood_group", bloodgrup.toString());
        builder.addFormDataPart("family_status", familystats);
        builder.addFormDataPart("no_of_family_members", nooffamily.toString());
        builder.addFormDataPart("physical_status", physicalstatus);
        builder.addFormDataPart("complexion", complexion);
        builder.addFormDataPart("eating_habits", eatinghabits.toString());
        builder.addFormDataPart("drinking_habits", drinkinghabits.toString());
        builder.addFormDataPart("smoking_habits", smoking_habits.toString());
        builder.addFormDataPart("highest_education", highest_qual);
        builder.addFormDataPart("annual_income", annual_income);
        builder.addFormDataPart("hobbies", hobbies);
        builder.addFormDataPart("interests", interest);
        builder.addFormDataPart("profile_for", "check");


        /*  builder.addFormDataPart("id", "10");
          builder.addFormDataPart("fullname", "jnfjjk");
          builder.addFormDataPart("email", "john@gmail.com");
          builder.addFormDataPart("dob", "");
          builder.addFormDataPart("sex", "0");
          builder.addFormDataPart("description", "dkld");
          builder.addFormDataPart("height", "0");
          builder.addFormDataPart("relationship_status", "0");
          builder.addFormDataPart("city", "1");
          builder.addFormDataPart("state", "2");
          builder.addFormDataPart("country", "0");
          builder.addFormDataPart("occupation", "service");
          builder.addFormDataPart("profile", "check");
          builder.addFormDataPart("zodiac", "0");
          builder.addFormDataPart("religion", "sas");
          builder.addFormDataPart("caste", "mddd");
          builder.addFormDataPart("mother_tongue", "ass");
          builder.addFormDataPart("blood_group", "0");
          builder.addFormDataPart("family_status", "sass");
          builder.addFormDataPart("no_of_family_members", "5");
          builder.addFormDataPart("physical_status", "sppp");
          builder.addFormDataPart("complexion", " compl");
          builder.addFormDataPart("eating_habits", "0");
          builder.addFormDataPart("drinking_habits","0");
          builder.addFormDataPart("smoking_habits", "0");
          builder.addFormDataPart("highest_education", "jdjd");
          builder.addFormDataPart("annual_income", "ddd");
          builder.addFormDataPart("hobbies", "hobbs");
          builder.addFormDataPart("interests", "ssas");
          builder.addFormDataPart("profile_for", "check");*/
        // builder.addFormDataPart("dp", "");


        //builder.addFormDataPart("profile_for_dp", "mobile.apps.pro.vn@gmail.com");
//       Log.e("filesemd",fileSend!!.name)
        fileSend?.let {
            Log.e("filesemd", "${fileSend.name}  if")
            builder.addFormDataPart(
                "dp",
                fileSend.name,
                RequestBody.create(MediaType.parse("multipart/form-data"), fileSend)
            );
        } ?: run {
            Log.e("filesemd", "$fileSend  else")
            builder.addFormDataPart("dp", "");
        }


        // Map is used to multipart the file using okhttp3.RequestBody
        // Multiple Images
        /* for (i in 0 until filePaths.size) {
             var file = File(filePaths.get(i));
             builder.addFormDataPart(
                 "file[]",
                 file.getName(),
                 RequestBody.create(MediaType.parse("multipart/form-data"), file)
             );
         }*/


        /* Call<ResponseBody> call = uploadService.uploadMultiFile(requestBody);
         call.enqueue(new Callback<ResponseBody>() {
             @Override
             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                 Toast.makeText(MainActivity.this, "Success " + response.message(), Toast.LENGTH_LONG).show();




             }

             @Override
             public void onFailure(Call<ResponseBody> call, Throwable t) {

                 Log.d(TAG, "Error " + t.getMessage());
             }
         });*/

        /*   val call: Observable<ResponsePojo?> = ApiClass.getApiServices(activity).userAccountDelete(
               id,
               fullname,
               email,
               sex,
               description,
               height,
               relationshipstatus,
               city,
               state,
               country,
               occupation,
               profile,
               zodiac,
               religion,
               caste,
               tongue,
               bloodgrup,
               familystats,
               nooffamily,
               physicalstatus,
               complexion,
               eatinghabits,
               drinkinghabits,
               smoking_habits,
               highest_qual,
               annual_income,
               hobbies,
               interest,
               profilefordp
           )*/

        var requestBody = builder.build();
        val call: Observable<ResponseBody?> =
            ApiClass.getApiServices(activity).userAccountDelete(requestBody)
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseBody?> {
                override fun onSubscribe(d: Disposable) { //  dialog.dismiss();
                    Log.e("userupdatepojo", "onSubscribe");
                }

                override fun onNext(responsebody: ResponseBody) {
                    dialog!!.dismiss()
                    // refreshFragemnt()
                    // Log.e("userupdatepojo", registerPojo.getMsg().toString() + "nnn")
                    Log.e("userupdatepojo", "responsePojo " + responsebody.toString());
//registerPojoMutableLiveData.setValue(registerPojo);
                }

                override fun onError(e: Throwable) {
                    dialog!!.dismiss()
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
                    Log.e("userupdatepojo", "" + e.localizedMessage)
                }

                override fun onComplete() {
                    dialog!!.dismiss()
                    // refreshFragemnt()
                    Log.e("userupdatepojo", "complete")
                }
            })
    }

    open fun showdialog() { // Toast.makeText(context, "showDialog", Toast.LENGTH_SHORT).show();
        dialog = Dialog(activity!!)
        dialog?.let {
            it.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_layout)
                val dialogText = findViewById(R.id.dialog_tv) as TextView
                dialogText.text = "Please wait...."
                val content = findViewById(R.id.content) as RippleBackground
                content.startRippleAnimation()
                setCancelable(false)
                getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                );
                window?.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
                show()
            }
        } ?: run {
            Toast.makeText(activity!!, "Dailog null", Toast.LENGTH_SHORT).show()
        }

    }
}
