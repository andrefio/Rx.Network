package io.andref.rx.network.example;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.andref.rx.network.RxNetwork;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity
{
    private Subscription mSubscription1;
    private Subscription mSubscription2;
    private Subscription mSubscription3;

    // region Activity Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView1 = (TextView) findViewById(R.id.text_view_1);
        final TextView textView2 = (TextView) findViewById(R.id.text_view_2);
        final TextView textView3 = (TextView) findViewById(R.id.text_view_3);

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);

        mSubscription1 = RxNetwork.connectivityChanges(this, connectivityManager)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>()
                {
                    @Override
                    public void call(Boolean isConnected)
                    {
                        if (isConnected)
                        {
                            textView1.setText(R.string.yes);
                        }
                        else
                        {
                            textView1.setText(R.string.no);
                        }
                    }
                });

        mSubscription2 = RxNetwork.stateChanges(this, connectivityManager)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NetworkInfo.State>()
                {
                    @Override
                    public void call(NetworkInfo.State state)
                    {
                        switch (state)
                        {
                            case CONNECTED:
                                textView2.setText(R.string.connected);
                                break;

                            case CONNECTING:
                                textView2.setText(R.string.connecting);

                            case DISCONNECTED:
                                textView2.setText(R.string.disconnected);
                                break;

                            case DISCONNECTING:
                                textView2.setText(R.string.disconnecting);
                                break;

                            case UNKNOWN:
                                textView2.setText(R.string.unknown);
                                break;

                        }
                    }
                });

        mSubscription3 = RxNetwork.detailedStateChanges(this, connectivityManager)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NetworkInfo.DetailedState>()
                {
                    @Override
                    public void call(NetworkInfo.DetailedState detailedState)
                    {
                        if (detailedState != null)
                        {
                            switch (detailedState)
                            {
                                case AUTHENTICATING:
                                    textView3.setText(R.string.authenticating);
                                    break;

                                case BLOCKED:
                                    textView3.setText(R.string.blocked);
                                    break;

                                case CAPTIVE_PORTAL_CHECK:
                                    textView3.setText(R.string.captive_portal_check);
                                    break;

                                case CONNECTED:
                                    textView3.setText(R.string.connected);
                                    break;

                                case CONNECTING:
                                    textView3.setText(R.string.connecting);
                                    break;

                                case DISCONNECTED:
                                    textView3.setText(R.string.disconnected);
                                    break;

                                case DISCONNECTING:
                                    textView3.setText(R.string.disconnecting);
                                    break;

                                case FAILED:
                                    textView3.setText(R.string.failed);
                                    break;

                                case IDLE:
                                    textView3.setText(R.string.idle);
                                    break;

                                case OBTAINING_IPADDR:
                                    textView3.setText(R.string.obtaining_ipaddr);
                                    break;

                                case SCANNING:
                                    textView3.setText(R.string.scanning);
                                    break;

                                case SUSPENDED:
                                    textView3.setText(R.string.suspended);
                                    break;

                                case VERIFYING_POOR_LINK:
                                    textView3.setText(R.string.verifying_poor_link);
                                    break;
                            }
                        }
                        else
                        {
                            textView3.setText(R.string.unknown);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        mSubscription1.unsubscribe();
        mSubscription2.unsubscribe();
        mSubscription3.unsubscribe();
    }

    // endregion
}