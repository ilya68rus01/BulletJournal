package khrushchev.ilya.bulletjournal

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import androidx.viewbinding.ViewBindings
import khrushchev.ilya.bulletjournal.databinding.ActivityMainBinding
import java.lang.RuntimeException
import java.security.acl.Owner
import kotlin.reflect.KProperty

class BindingDelegate<T: ViewBinding>(
    private val viewBindingFactory: (view: View) -> T
) {
    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>) : T {
        var binding: T? = viewBindingFactory.invoke(
            thisRef.findViewById<ViewGroup>(android.R.id.content)
                .getChildAt(0)
        )

        val callback = LifecycleEventObserver { source, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                binding = null
            }
        }
        thisRef.lifecycle.addObserver(callback)
        return binding ?: throw RuntimeException("Not implemented")
    }
}

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by BindingDelegate(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, TodoListFragment())
            .commit()
    }
}