package com.example.app1

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        masked_et.setIconCallback {
            masked_et.setMaskedText("")
        }
        masked_et.doOnTextChanged { text, start, before, count ->
            if (start == 14) {
                continue_b.isClickable = true
                continue_b.setTextColor(context?.let { ContextCompat.getColor(it, R.color.black) }!!)
            } else {
                continue_b.isClickable = false
                continue_b.setTextColor(context?.let { ContextCompat.getColor(it, R.color.gray) }!!)
            }
        }
        agreement_tv.text = spanText()
        agreement_tv.movementMethod = LinkMovementMethod.getInstance()

        close_iv.setOnClickListener {
            Toast.makeText(context, "Пока", Toast.LENGTH_SHORT).show()
        }

        continue_b.setOnClickListener {
            Toast.makeText(context, masked_et.unmaskedText, Toast.LENGTH_SHORT).show()
        }
    }

    private fun spanText(): SpannableString {
        val spanText =
            SpannableString("Нажимая \"Продолжить\", я соглашаюсь с Публичной \n офертой и Соглошением о конфеденциальности")

        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(context, "Публичной офертой", Toast.LENGTH_SHORT).show()
            }
        }

        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(context, "Соглошением о конфеденциальности", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        spanText.setSpan(clickableSpan1, 37, 54, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanText.setSpan(clickableSpan2, 57, 89, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spanText
    }
}