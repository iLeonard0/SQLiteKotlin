package unipar.edu.utfpr.usandosqlitepos2024

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.usandosqlite_pos2024.entity.Cadastro
import unipar.edu.utfpr.usandosqlitepos2024.database.DatabaseHandler
import unipar.edu.utfpr.usandosqlitepos2024.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() { //fim da MainActivity

    private lateinit var binding: ActivityMainBinding
    private lateinit var banco: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonListener()

        binding.etCod.setEnabled(false)

        if (intent.getIntExtra("cod", 0) != 0) {
            binding.etCod.setText(intent.getIntExtra("cod", 0).toString())
            binding.etNome.setText(intent.getStringExtra("nome"))
            binding.etTelefone.setText(intent.getStringExtra("telefone"))
        } else {
            binding.btExcluir.visibility = View.GONE
            binding.btPesquisar.visibility = View.GONE
        }

        banco = DatabaseHandler(this)

        System.out.println("onCreate() executado")
    }

    private fun setButtonListener() {

        binding.btSalvar.setOnClickListener {
            btSalvarOnClick()
        }

        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }

        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }

    }


    private fun btSalvarOnClick() {
        if (binding.etCod.text.isEmpty()) {
            banco.insert(
                Cadastro(
                    0,
                    binding.etNome.text.toString(),
                    binding.etTelefone.text.toString()
                )
            )
            Toast.makeText(this, "Sucesso!!!", Toast.LENGTH_LONG).show()
        } else {
            banco.update(
                Cadastro(
                    binding.etCod.text.toString().toInt(),
                    binding.etNome.text.toString(),
                    binding.etTelefone.text.toString()
                )
            )
            Toast.makeText(this, "Sucesso!!!", Toast.LENGTH_LONG).show()
        }

        finish()
    }

    private fun btExcluirOnClick() {
        banco.delete(binding.etCod.text.toString().toInt())
        Toast.makeText(this, "Sucesso!!!", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun btPesquisarOnClick() {

        val builder = AlertDialog.Builder(this)

        val etCodPesquisar = EditText(this)

        builder.setTitle("Digite o código da pesquisa")
        builder.setView(etCodPesquisar)
        builder.setCancelable(false)
        builder.setNegativeButton("Fechar", null)
        builder.setPositiveButton(
            "Pesquisar",
            DialogInterface.OnClickListener { dialogInterface, i ->

                val registro = banco.find(etCodPesquisar.text.toString().toInt())

                if (registro != null) {
                    binding.etCod.setText(etCodPesquisar.text.toString())
                    binding.etNome.setText(registro.nome)
                    binding.etTelefone.setText(registro.telefone)
                } else {
                    Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_LONG).show()
                }


            }
        )

        builder.show();

        /**/
    }

    private fun btPListarOnClick() {
        val intent = Intent(this, ListarActivity::class.java)
        startActivity(intent)

    }

    override fun onStart() {
        super.onStart()
        System.out.println("onStart() executado")
    }

    override fun onResume() {
        super.onResume()
        System.out.println("onResume() executado")
    }

    override fun onPause() {
        super.onPause()
        System.out.println("onPause() executado")
    }

    override fun onStop() {
        super.onStop()
        System.out.println("onStop() executado")
    }

    override fun onDestroy() {
        super.onDestroy()
        System.out.println("onDestroy() executado")
    }

    override fun onRestart() {
        super.onRestart()
        System.out.println("onDestroy() executado")
    }
}
