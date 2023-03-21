package com.example.intent

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.intent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object Constantes {
        const val PARAMETRO_EXTRA = "PARAMETRO_EXTRA"
        const val PARAMETRO_ACTIVITY_REQUEST_CODE = 0
        const val TELA2_ACTIVITY_REQUEST_CODE = 1
    }

    private lateinit var parl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        parl = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object:ActivityResultCallback<ActivityResult>{
            override fun onActivityResult(result: ActivityResult?) {
                if (result?.resultCode == RESULT_OK){
                    val retorno: String = result.data?.getStringExtra(PARAMETRO_EXTRA) ?: ""
                    amb.parametroTv.text = retorno
                }
            }
        })

        amb.entrarParametroBt.setOnClickListener{
            val parametroIntent = Intent("SANTOS_BI_MUNDIAL")
            parametroIntent.putExtra(PARAMETRO_EXTRA, amb.parametroTv.text.toString())
            parl.launch(parametroIntent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.viewMi -> {
                val url: Uri = Uri.parse(amb.parametroTv.text.toString())
                val navegadorIntent :  Intent = Intent(Intent.ACTION_VIEW, url)
                startActivity(navegadorIntent)
                true
            }
            R.id.callMi -> true
            R.id.dialMi -> true
            R.id.pickMi -> true
            R.id.chooserMi -> true
            else -> false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PARAMETRO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            val retorno: String = data?.getStringExtra(PARAMETRO_EXTRA) ?: ""
            amb.parametroTv.text = retorno
        }
    }
}