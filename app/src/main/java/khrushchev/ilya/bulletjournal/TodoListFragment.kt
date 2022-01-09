package khrushchev.ilya.bulletjournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.viewbinding.ViewBinding
import khrushchev.ilya.bulletjournal.databinding.ActivityMainBinding
import khrushchev.ilya.bulletjournal.databinding.FragmentTodoListBinding
import java.lang.RuntimeException
import kotlin.reflect.KProperty

inline fun <F: Fragment, T : ViewBinding> viewBinding(
    noinline viewBindingFactory: (View) -> T,
    noinline viewBindingHolder: (F) -> View = Fragment::requireView
): FragmentBindingDelegate<F, T>{
    return FragmentBindingDelegate(viewBindingFactory,viewBindingHolder)
}

class FragmentBindingDelegate<F: Fragment, T: ViewBinding>(
    private val viewBindingFactory: (View) -> T,
    private val viewBindingHolder: (F) -> View
) {
    operator fun getValue(thisRef: F, property: KProperty<*>) : T {
        var binding: T? = viewBindingFactory.invoke(viewBindingHolder.invoke(thisRef))

        val callback = LifecycleEventObserver { source, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                binding = null
            }
        }
        thisRef.lifecycle.addObserver(callback)
        return binding ?: throw RuntimeException("Not implemented")
    }
}

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {

    private val binding by viewBinding(FragmentTodoListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mark.taskState = TaskState.CREATED
    }
}