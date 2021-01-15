package com.example.app1

import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.app1.databinding.FragmentAuthBinding
import kotlinx.android.synthetic.main.fragment_auth.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class AuthFragment : BindingFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            toolbar.inflateMenu(R.menu.menu)
            toolbar.setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }
            val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
            mask.isForbidInputWhenFilled = true
            val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)
            formatWatcher.installOn(etPhoneNumber)

            filledTextInputLayout.setEndIconOnClickListener {
                etPhoneNumber.setText("", TextView.BufferType.EDITABLE)
            }
            etPhoneNumber.doOnTextChanged { text, start, before, count ->
                if (start == 17) {
                    btnContinue.isClickable = true
                    btnContinue.setOnClickListener {
                        context?.toast(etPhoneNumber.text.toString())
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.close_icon -> context?.toast("Good bye")
        }
        return true
    }
}