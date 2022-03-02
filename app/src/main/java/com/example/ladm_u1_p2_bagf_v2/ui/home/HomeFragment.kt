package com.example.ladm_u1_p2_bagf_v2.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ladm_u1_p2_bagf_v2.CustomAdapter
import com.example.ladm_u1_p2_bagf_v2.CustomAdapter.Companion.tasks
import com.example.ladm_u1_p2_bagf_v2.R
import com.example.ladm_u1_p2_bagf_v2.databinding.FragmentHomeBinding
import java.io.IOException
import java.io.OutputStreamWriter
import java.lang.Exception

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        try {
            // Variables recycleView
            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
            val adapter = CustomAdapter()
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            adapter.leer(requireContext())
            // Variables recycleView

            // Mostrar aviso
            val aviso = root.findViewById<TextView>(R.id.aviso)
            if (tasks.size == 0) {
                aviso.setText("¡No tienes tareas por el momento! :)")
            } else {
                aviso.textSize = 10f
                aviso.setText("Aviso\nCuando se borra una tarea y se vuelve a entrar a 'Tareas Pendientes', se lanza una excepción del tipo 'IndexOutOfBounds' en el 'onBindViewHolder' del 'CustomAdapter.kt'\nCreo que se debe a que cuando se borra el elemento 'n' del 'arrayTareas', el elemento siguiente se convierte en el 'n' elemento y el elemento 'n + 1' produce el error. \n En sí la app agrega, actualiza y elimina, solo sería ese detalle del recycleView.\nPara comprobar borre un elemento en 'Tareas Pendientes'; verifique en el spinner del fragment 'Editar Tareas' que se borró ese elemento (solo no vuelva a entrar a 'Tareas Pendientes' ya que se crashea)")            }

        } catch (e: Exception) {
            println("maldita pinche sea")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}