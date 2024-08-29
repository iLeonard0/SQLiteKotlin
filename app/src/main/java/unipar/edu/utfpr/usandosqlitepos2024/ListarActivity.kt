package unipar.edu.utfpr.usandosqlitepos2024

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import unipar.edu.utfpr.usandosqlitepos2024.adapter.MeuAdapter
import unipar.edu.utfpr.usandosqlitepos2024.database.DatabaseHandler
import unipar.edu.utfpr.usandosqlitepos2024.databinding.ActivityListarBinding

class ListarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListarBinding
    private lateinit var banco : DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate( layoutInflater )
        setContentView(binding.root)

        banco = DatabaseHandler( this )

        binding.btIncluir.setOnClickListener{
            btIncluirOnClick()
        }

    }

    private fun btIncluirOnClick() {
        val intent = Intent( this, MainActivity::class.java )
        startActivity( intent )
    }

    override fun onStart() {
        super.onStart()
        val banco = Firebase.firestore
        banco.collection("cadastro")
            .get()
            .addOnSuccessListener { result ->
                var registros = mutableListOf<Cadastro>()
                for (document in result) {
                    val cadastro = Cadastro(
                        document.data.get("_id").toString().toInt(),
                        document.data.get("nome").toString(),
                        document.data.get("telefone").toString()
                    )
                    registros.add(cadastro)
                }
                val adapter = MeuAdapter( this, registros )
                binding.lvPrincipal.adapter = adapter //destino
            }
            .addOnFailureListener { e ->
                println("Erro${e.message}")
            }
        // val registros = banco.cursorList() //fonte  tem que ter selecionado o campo _id, com exatamente este nome
    }
}