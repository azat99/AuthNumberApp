package com.example.app1.view

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.app1.R
import com.example.app1.databinding.FragmentAuthBinding
import com.example.app1.repositories.AuthRepository
import com.example.app1.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class AuthFragment : BindingFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.close_icon) {
                     context?.toast("Good bye")
                }
                false
            }

            etPhoneNumber.doOnTextChanged { text, start, before, count ->
                if (text?.length==11) {
                    btnContinue.isClickable = true
                    btnContinue.setOnClickListener {
                        getNumber(text.toString(),it)
                    }
                    btnContinue.setTextColor(context?.let {
                        ContextCompat.getColor(
                            it,
                            R.color.black
                        )
                    }!!)
                } else {
                    btnContinue.isClickable = false
                    btnContinue.setTextColor(context?.let {
                        ContextCompat.getColor(
                            it,
                            R.color.gray
                        )
                    }!!)
                }
            }
            tvSpanText.text = spanText()
            tvSpanText.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun spanText(): SpannableString {
        val spanText =
            SpannableString(resources.getString(R.string.spanText))

        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                context?.toast("Публичной офертой")
            }
        }

        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                context?.toast("Соглошением о конфеденциальности")
            }
        }

        spanText.setSpan(clickableSpan1, 37, 56, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanText.setSpan(clickableSpan2, 59, 91, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spanText
    }

    private fun getNumber(number : String,view:View){
        val authRepository = AuthRepository()
        CoroutineScope(Dispatchers.Main).launch {
            //val status = authRepository.check("77026328252")
            //context?.toast(status)
            when (authRepository.check(number)){
                204 -> {
                    val action = AuthFragmentDirections.actionAuthFragmentToRegisterFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                200 -> context?.toast("номер зарегистрирован и пароль установлен, переход к аунтерификации")
                409 -> context?.toast("номер зарегистрирован и пароль еще не установлен")
            }
        }
    }

}