package mx.araiza.superherotest.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import mx.araiza.superherotest.R
import mx.araiza.superherotest.model.API.Constants
import mx.araiza.superherotest.utils.SuperheroesSP
import mx.araiza.superherotest.model.database.SuperheroDB
import mx.araiza.superherotest.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    lateinit var loadingDialog :AlertDialog
    lateinit var  database :SuperheroDB
    val viewModel :MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = SuperheroDB.getDatabase(this)

        loadingDialog = MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setView(R.layout.dialog_loading).create()

        loadingDialog.show()

        database.getSuperheroDAO().countSavedHeroes().observe(this){
            if (it > 10) loadingDialog.dismiss()
        }
        viewModel.privateKey = resources.getString(R.string.PRIVATE_KEY)
        viewModel.publicKey = resources.getString(R.string.PUBLIC_KEY)
//
//        Constants.privateKey = resources.getString(R.string.PRIVATE_KEY)
//        Constants.publicKey = resources.getString(R.string.PUBLIC_KEY)

        viewModel.onLoad(database.getSuperheroDAO(), SuperheroesSP(this))
    }
}