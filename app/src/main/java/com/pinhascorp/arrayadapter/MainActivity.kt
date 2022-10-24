package com.pinhascorp.arrayadapter

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.pinhascorp.arrayadapter.databinding.ActivityMainBinding
import com.pinhascorp.arrayadapter.databinding.DialogAddCharacterBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<Character>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListWithArrayAdapter()
        binding.addBtn.setOnClickListener { onAddPressed() }

    }

    private fun setupListWithArrayAdapter(){
        val data:MutableList<Character> = mutableListOf(
            Character(id = UUID.randomUUID().toString(), name = "Aslan"),
            Character(id = UUID.randomUUID().toString(), name = "Ilya"),
            Character(id = UUID.randomUUID().toString(), name = "Huyna")
        )
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            data
        )

        binding.arrayListView.adapter = adapter

        binding.arrayListView.setOnItemClickListener { adapterView, view, position, l ->
            adapter.getItem(position)?.let {
                deleteCharacter(it)
            }
        }
    }

    private fun onAddPressed(){
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setTitle("CreateCharacter")
            .setView(dialogBinding.root)
            .setPositiveButton("add"){d, which ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if(name.isNotBlank())
                    createCharacter(name)
            }
            .create()
        dialog.show()
    }

    private fun deleteCharacter(character: Character){
        val listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE)
                adapter.remove(character)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete Character?")
            .setMessage("Are you sure want to delete the character '{charact...")
            .setPositiveButton("delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }

    private fun createCharacter(name: String){
        val character = Character(
            id = UUID.randomUUID().toString(),
            name = name
        )

        adapter.add(character)
    }

    class Character(
        val id: String,
        val name: String
    ){
        override fun toString(): String {
            return name
        }
    }
}