package com.example.loanapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loanapp.PreLoginActivity
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentSlideshowBinding
import com.example.loanapp.models.Loan
import com.example.loanapp.utils.GenericSimpleRecyclerBindingInterface
import com.example.loanapp.utils.NavigationUtils
import com.example.loanapp.utils.SimpleGenericRecyclerAdapter
import com.example.loanapp.utils.StorageUtils
import com.example.loanapp.utils.ToastUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var mobilenum=""
    val loanList=ArrayList<Loan>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mobilenum= StorageUtils.fetchString("mobile","")!!
        /*val textView: TextView = binding.textSlideshow
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        binding.imgUsericon.setOnClickListener {
            val bundle=Bundle()
            bundle.putBoolean("isSignIn",false)
            bundle.putBoolean("isUpdate",true)
            NavigationUtils.goToFragment(activity=requireActivity(),bundle=bundle, navigateToId = R.id.action_nav_slideshow_to_nav_home)

        }

        binding.applyLoanButton.setOnClickListener {
            showInputDialog()
        }


        binding.viewLoansButton.setOnClickListener {
            callApiViewLoan()
        }

        binding.imgBack.setOnClickListener {
            openHomeView()
        }

        initiate()

        return root
    }

    fun callApiViewLoan(){
        val map=HashMap<String,String>()
        map.put("mobile",mobilenum)


        (activity as PreLoginActivity).showProgressDialog(true,"Calling Api view loan...")





        (activity as PreLoginActivity).preLoginActivityViewModel.callViewLoan(
            map
        )
    }

    private fun showInputDialog() {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_enter_details, null)

        // Get the EditText fields from the custom layout
        val editTextAmount = dialogView.findViewById<EditText>(R.id.usernameEditText)
        val editTextDuration = dialogView.findViewById<EditText>(R.id.passwordEditText)

        // Create the AlertDialog
        val dialog = android.app.AlertDialog.Builder((activity as PreLoginActivity))
            .setTitle("Apply Loan Details")
            .setView(dialogView)
            .setPositiveButton("Submit") { dialog, _ ->
                // Handle the submit action
                val amount = editTextAmount.text.toString()
                val duration = editTextDuration.text.toString()

                if (amount.isNotEmpty() && duration.isNotEmpty()) {
                    // Do something with the input data
                    val map=HashMap<String,String>()
                    map.put("mobile",mobilenum)
                    map.put("loan_amount",amount)
                    map.put("loan_duration",duration)


                    (activity as PreLoginActivity).showProgressDialog(true,"Call Api Applying loan...")





                            (activity as PreLoginActivity).preLoginActivityViewModel.callApplyLoan(
                                map
                            )


                } else {
                    ToastUtils.showLongToast("Please enter both fields")
                }

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // Handle the cancel action
                dialog.dismiss()
            }
            .create()

        // Show the dialog
        dialog.show()
    }

    private fun initiate() {
        (activity as PreLoginActivity).setViewModel()
        (activity as PreLoginActivity).preLoginActivityViewModel.clearApplyLoanResponseHandler()
        (activity as PreLoginActivity).preLoginActivityViewModel.clearViewLoanResponseHandler()

        (activity as PreLoginActivity).preLoginActivityViewModel.apiApplyLoan.observe(viewLifecycleOwner){
            if (it != null) {
                (activity as PreLoginActivity).showProgressDialog(false,"Loading...")
                if (it.observeStatus)
                    callApiViewLoan()
            }
        }

        (activity as PreLoginActivity).preLoginActivityViewModel.apiViewLoan.observe(viewLifecycleOwner){
            if (it != null) {
                (activity as PreLoginActivity).showProgressDialog(false,"Loading...")

                it.loans?.let { it1 -> onSuccessApplyLoan(it1) }
            }
        }


    }

    private val bindingGroupInterface = object :
        GenericSimpleRecyclerBindingInterface<Loan> {
        override fun bindData(item: Loan, view: View) {
            val amount: TextView = view.findViewById(R.id.textView21)
            val duration: TextView = view.findViewById(R.id.textView20)
            val status: TextView = view.findViewById(R.id.textView39)

            val cardView: CardView = view.findViewById(R.id.schemeCard)

            amount.text=item.amount.toString()
            duration.text="Duration :- "+item.duration.toString()
            status.text="Status :- "+item.status.toString()



            cardView.setOnClickListener {




            }

        }
    }

    private var adapter =  SimpleGenericRecyclerAdapter(loanList, R.layout.group_schemes_item, bindingGroupInterface)

    private fun onSuccessApplyLoan(it: List<Loan>) {
        loanList.clear()
        loanList.addAll(it)
        binding.recyclerview.visibility=View.VISIBLE
        binding.recyclerview.adapter=adapter
        binding.linForm.visibility=View.GONE
        binding.imgBack.visibility=View.VISIBLE
        binding.textHome.text="Loan History"

    }


    fun openHomeView(){
        binding.recyclerview.visibility=View.GONE
        binding.linForm.visibility=View.VISIBLE
        binding.imgBack.visibility=View.GONE
        binding.textHome.text="Home"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}