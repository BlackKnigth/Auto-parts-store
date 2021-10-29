package com.example.auto_parts_store

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.auto_parts_store.ui.main.MainFragment
import org.jetbrains.anko.doAsync
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var vinField: EditText? = null
    private var decodedResult: TextView? = null
    private var decodeBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        vinField = findViewById(R.id.vin_field)
        decodeBtn = findViewById(R.id.decode_btn)
        decodedResult = findViewById(R.id.result_view)

        decodeBtn?.setOnClickListener {
            decodedResult?.text = ""
            if(vinField?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Введите VIN", Toast.LENGTH_LONG).show()
            else {
                val api_url =
                    "https://vpic.nhtsa.dot.gov/api/vehicles/decodevin/${vinField?.text}?format=json"

                doAsync {
                    val api_responce = URL(api_url).readText()
                    Log.d("INFO", api_responce)

                    decodedResult?.text = api_responce
                }
            }
        }
    }
}