package com.example.loanapp

import android.os.Bundle
import android.view.Menu
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.loanapp.databinding.ContentMainBinding
import com.example.loanapp.utils.NavigationUtils
import com.example.loanapp.viewmodels.LoanAppViewModelFactory
import com.example.loanapp.viewmodels.PreLoginViewModel

class PreLoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ContentMainBinding
    lateinit var preLoginActivityViewModel: PreLoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install the splash screen

        super.onCreate(savedInstanceState)

        binding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setViewModel()



    }

     fun setViewModel() {

        preLoginActivityViewModel = ViewModelProvider(
            this,
            LoanAppViewModelFactory()
        ).get(PreLoginViewModel::class.java)



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private var progressDialog: AlertDialog? = null
    fun showProgressDialog(show: Boolean, message: String) {
        if (show) {
            if (progressDialog == null) {
                val builder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                val dialogView = inflater.inflate(R.layout.progress_dialog_layout, null)
                val progressBar: ProgressBar = dialogView.findViewById(R.id.progressBar)
                val messageTextView: TextView = dialogView.findViewById(R.id.messageTextView)
                messageTextView.text = message

                builder.setView(dialogView)
                builder.setCancelable(false)

                progressDialog = builder.create()
                progressDialog?.show()
            }
        } else {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    override fun onBackPressed() {

       // super.onBackPressed()
        if (preLoginActivityViewModel!=null)
        NavigationUtils.popBackStack(this)
        else
            super.onBackPressed()
    }
}