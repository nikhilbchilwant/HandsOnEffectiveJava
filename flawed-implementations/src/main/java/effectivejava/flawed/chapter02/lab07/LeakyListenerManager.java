package effectivejava.flawed.chapter02.lab07;

import java.util.ArrayList;
import java.util.List;

/**
 * FLAWED IMPLEMENTATION - Listener/callback leak
 * 
 * This is a common pattern in GUIs, event systems, and reactive programming.
 * Listeners are registered but never unregistered, causing memory leaks.
 */
public class LeakyListenerManager<T> {

    // Strong references to all listeners - they can never be GC'd!
    private final List<EventListener<T>> listeners = new ArrayList<>();

    /**
     * Register a listener for events.
     * PROBLEM: No way to know when listener should be removed.
     * PROBLEM: Caller might not remember to unregister.
     */
    public void addListener(EventListener<T> listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener - but caller must remember to call this!
     * If they don't (or can't), the listener is retained forever.
     */
    public boolean removeListener(EventListener<T> listener) {
        return listeners.remove(listener);
    }

    /**
     * Fire an event to all listeners.
     */
    public void fireEvent(T event) {
        for (EventListener<T> listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                // Don't let one bad listener stop others
                System.err.println("Listener threw exception: " + e);
            }
        }
    }

    public int getListenerCount() {
        return listeners.size();
    }

    /**
     * Simple event listener interface.
     */
    @FunctionalInterface
    public interface EventListener<T> {
        void onEvent(T event);
    }

    // MISSING:
    // - WeakReference to listeners so they can be GC'd
    // - Auto-cleanup of dead listeners before each fireEvent
    // - Documentation requiring explicit removal
}
