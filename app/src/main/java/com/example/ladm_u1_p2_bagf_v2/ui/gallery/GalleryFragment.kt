package com.example.ladm_u1_p2_bagf_v2.ui.gallery

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ladm_u1_p2_bagf_v2.CustomAdapter.Companion.details
import com.example.ladm_u1_p2_bagf_v2.CustomAdapter.Companion.tasks
import com.example.ladm_u1_p2_bagf_v2.R
import com.example.ladm_u1_p2_bagf_v2.databinding.FragmentGalleryBinding
import java.io.IOException
import java.io.OutputStreamWriter

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nombreTarea = root.findViewById<EditText>(R.id.nombre_tarea)
        val descripcionTarea = root.findViewById<EditText>(R.id.nombre_descripcion)
        val btnAgregarTarea = root.findViewById<Button>(R.id.btnAgregarTareas)

        btnAgregarTarea.setOnClickListener {
            try {
                val cadena = nombreTarea.text.toString() + "," + descripcionTarea.text.toString()
                // Guardar en archivo
                guardar(cadena, requireActivity())

                // Formularios
                nombreTarea.setText("Tarea ")
                descripcionTarea.setText("Descripción ")
            } catch (e: Exception) {
                AlertDialog.Builder(requireContext())
                    .setMessage(e.message).show()
            }
        }
        return root
    }

    fun guardar(data: String, context: Context) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(context.openFileOutput("datos.txt", Context.MODE_APPEND))
            outputStreamWriter.write(data + "\n")
            outputStreamWriter.flush()
            outputStreamWriter.close()

            AlertDialog.Builder(requireContext())
                .setMessage("¡Se guardó la tarea!").show()
            //Toast.makeText(context, "¡Se guardó la tarea!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            println(e.message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}