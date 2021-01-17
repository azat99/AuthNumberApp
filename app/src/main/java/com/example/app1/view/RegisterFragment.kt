package com.example.app1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.app1.R
import com.example.app1.databinding.FragmentRegisterBinding
import com.example.app1.repositories.AuthRepository
import com.example.app1.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : BindingFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate){

    private val authRepository = AuthRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {

            when (authRepository.code("77471895834")) {
                200 -> context?.toast("номер не зарегистрирован, переход к проверке смс кода.")
                226 -> context?.toast("номер зарегистрирован и пароль установлен, переход к аунтерификации")
                409 -> context?.toast("номер зарегистрирован и пароль еще не установлен")
            }
        }

        binding.run {
            materialToolbar.setNavigationOnClickListener {
                context?.toast("back")
            }
        }

    }

}