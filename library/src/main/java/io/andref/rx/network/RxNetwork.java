package io.andref.rx.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.andref.rx.content.ContentObservable;
import rx.Observable;
import rx.functions.Func1;

/**
 * Observe the changing state of the active network.
 *
 * You need to add the ACCESS_NETWORK_STATE permission to the AndroidManifest.xml.
 * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
 */
public class RxNetwork
{
    /**
     * Returns an observable that observes connectivity changes to the active network.
     *
     * @param context Context required to observe system broadcasts.
     * @param connectivityManager Connectivity Manager from which we'll get the active network.
     * @return Boolean representing whether the network is connected and usable.
     */
    public static Observable<Boolean> connectivityChanges(final Context context, final ConnectivityManager connectivityManager)
    {
        final IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        return ContentObservable.fromBroadcast(context, intentFilter)
                .map(new Func1<Intent, Boolean>()
                {
                    @Override
                    public Boolean call(Intent ignored)
                    {
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        return networkInfo != null && networkInfo.isConnected();
                    }
                })
                .distinctUntilChanged();
    }

    /**
     * Returns an observable that observes connectivity changes to the active network.
     *
     * @param context Context required to observe system broadcasts.
     * @param connectivityManager Connectivity Manager from which we'll get the active network.
     * @return NetworkInfo.State
     */
    public static Observable<NetworkInfo.State> stateChanges(final Context context, final ConnectivityManager connectivityManager)
    {
        final IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        return ContentObservable.fromBroadcast(context, intentFilter)
                .map(new Func1<Intent, NetworkInfo.State>()
                {
                    @Override
                    public NetworkInfo.State call(Intent ignored)
                    {
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if (networkInfo != null)
                        {
                            return networkInfo.getState();
                        }

                        return NetworkInfo.State.UNKNOWN;
                    }
                })
                .distinctUntilChanged();
    }

    /**
     * Returns an observable that observes connectivity changes to the active network.
     *
     * @param context Context required to observe system broadcasts.
     * @param connectivityManager Connectivity Manager from which we'll get the active network.
     * @return NetworkInfo.DetailedState
     */
    public static Observable<NetworkInfo.DetailedState> detailedStateChanges(final Context context, final ConnectivityManager connectivityManager)
    {
        final IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        return ContentObservable.fromBroadcast(context, intentFilter)
                .map(new Func1<Intent, NetworkInfo.DetailedState>()
                {
                    @Override
                    public NetworkInfo.DetailedState call(Intent ignored)
                    {
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if (networkInfo != null)
                        {
                            return networkInfo.getDetailedState();
                        }

                        return null;
                    }
                })
                .distinctUntilChanged();
    }
}