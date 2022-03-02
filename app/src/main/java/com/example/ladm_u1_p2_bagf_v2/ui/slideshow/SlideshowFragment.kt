package com.example.ladm_u1_p2_bagf_v2.ui.slideshow

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ladm_u1_p2_bagf_v2.CustomAdapter.Companion.details
import com.example.ladm_u1_p2_bagf_v2.CustomAdapter.Companion.index
import com.example.ladm_u1_p2_bagf_v2.CustomAdapter.Companion.tasks
import com.example.ladm_u1_p2_bagf_v2.R
import com.example.ladm_u1_p2_bagf_v2.databinding.FragmentSlideshowBinding
import java.io.IOException
import java.io.OutputStreamWriter

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner = root.findViewById<Spinner>(R.id.tareas_spinner)
        val tarea = root.findViewById<EditText>(R.id.nombre_tarea_editar)
        val descripcion = root.findViewById<EditText>(R.id.nombre_descripcion_editar)
        // val btnBorrarTarea = root.findViewById<Button>(R.id.btnBorrarTarea)
        val btnBorrarTodo = root.findViewById<Button>(R.id.borrarTodo)
        val btnGuardar = root.findViewById<Button>(R.id.btnEditarTareas)

        try {

            btnGuardar.setOnClickListener {
                editar(index, tarea.text.toString(), descripcion.text.toString())
            }

            btnBorrarTodo.setOnClickListener {
                if (tasks.size > 0) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Se borraron todas las tareas").show()
                    guardar("", requireContext())
                } else {
                    AlertDialog.Builder(requireContext())
                        .setMessage("No hay tareas guardadas. Añade una para comenzar.").show()
                }
            }

            val adaptador =
                ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    tasks
                )
            spinner.adapter = adaptador
        } catch (e: java.lang.Exception) {
            AlertDialog.Builder(requireContext())
                .setMessage("Spinner.adapter == null, Agrega una tarea y vuelve a cargar.").show()
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                try {
                    println("Index Spinner: $position")
                    tarea.setText(tasks[position])
                    descripcion.setText(details[position])
                    index = position
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), e.message, Toast.LENGTH_LONG).show()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }
        return root
    }

    private fun editar(index: Int, tarea: String, desc: String) {

        // Reemplazar valores
        for (i in 0 until tasks.size) {
            if (index == i) {
                tasks[i] = tarea
                details[i] = desc
            }
        }

        // Procesar cadena
        var cad = ""
        for (i in 0 until tasks.size) {
            println("for $i")
            val t = tasks[i]
            val d = details[i]
            cad += t + ", " + d + "\n"
        }
        println(cad)

        // Guardar la cadena en txt
        try {
            val outputStreamWriter =
                OutputStreamWriter(
                    requireContext().openFileOutput(
                        "datos.txt",
                        Context.MODE_PRIVATE
                    )
                )
            outputStreamWriter.write(cad + "\n")
            outputStreamWriter.flush()
            outputStreamWriter.close()

            AlertDialog.Builder(requireContext())
                .setMessage("Se modificó la tarea, vuelve a cargar la pantalla para visualizar cambios.").show()
        } catch (e: IOException) {
            println(e.message)
        }
    }

    fun guardar(data: String, context: Context) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(context.openFileOutput("datos.txt", Context.MODE_PRIVATE))
            outputStreamWriter.write(data + "\n")
            outputStreamWriter.flush()
            outputStreamWriter.close()
        } catch (e: IOException) {
            println(e.message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}