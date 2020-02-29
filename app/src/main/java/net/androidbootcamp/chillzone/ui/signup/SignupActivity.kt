package net.androidbootcamp.chillzone.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.R
import net.androidbootcamp.chillzone.databinding.ActivitySignupBinding
import net.androidbootcamp.chillzone.ui.login.LoginViewModel
import net.androidbootcamp.chillzone.viewModels.VMFactory
import javax.inject.Inject

class SignupActivity : AppCompatActivity() {

    lateinit var loginViewModel:LoginViewModel
    @Inject lateinit var vmFactory: VMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as ChillApp).appComponent.inject(this)
        val binding: ActivitySignupBinding  =  DataBindingUtil.setContentView(this,
            R.layout.activity_signup)

        loginViewModel = vmFactory.create(LoginViewModel::class.java)
        binding.state = loginViewModel

    }
}
