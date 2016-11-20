package io.andref.rx.network.example;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.andref.rx.network.RxNetwork;
import io.andref.rx.widgets.ListViewCard;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity
{
    private CompositeSubscription mCompositeSubscription;
    private ConnectivityManager mConnectivityManager;
    private List<ListViewCard.Item> mItems = new ArrayList<>();
    private ListViewCard mListViewCard;

    // region Activity Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        
        mItems.add(0, new ListViewCard.Item(getString(R.string.network_connection), "", R.drawable.ic_router_black_24dp, 0));
        mItems.add(1, new ListViewCard.Item(getString(R.string.coarse_network_state), "", R.drawable.ic_wifi_black_24dp, 0));
        mItems.add(2, new ListViewCard.Item(getString(R.string.detailed_network_state), "", R.drawable.ic_wifi_tethering_black_24dp, 0));

        mListViewCard = (ListViewCard) findViewById(R.id.list_view_card);
        mListViewCard.setItems(mItems);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mCompositeSubscription.unsubscribe();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        mCompositeSubscription = new CompositeSubscription();
        
        mCompositeSubscription.add(
                RxNetwork.connectivityChanges(this, mConnectivityManager)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Boolean>()
                        {
                            @Override
                            public void call(Boolean connected)
                            {
                                ListViewCard.Item item = mItems.get(0);
                                item.setLine2(connected ? getString(R.string.yes) : getString(R.string.no));
                                
                                mListViewCard.setItems(mItems);
                            }
                        })
        );
        
        mCompositeSubscription.add(
                RxNetwork.stateChanges(this, mConnectivityManager)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<NetworkInfo.State>()
                        {
                            @Override
                            public void call(NetworkInfo.State state)
                            {
                                ListViewCard.Item item = mItems.get(1);

                                switch (state)
                                {
                                    case CONNECTED:
                                        item.setLine2(getString(R.string.connected));
                                        break;

                                    case CONNECTING:
                                        item.setLine2(getString(R.string.connecting));

                                    case DISCONNECTED:
                                        item.setLine2(getString(R.string.disconnected));
                                        break;

                                    case DISCONNECTING:
                                        item.setLine2(getString(R.string.disconnecting));
                                        break;

                                    case UNKNOWN:
                                        item.setLine2(getString(R.string.unknown));
                                        break;
                                }

                                mListViewCard.setItems(mItems);
                            }
                        })
        );

        mCompositeSubscription.add(
                RxNetwork.detailedStateChanges(this, mConnectivityManager)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<NetworkInfo.DetailedState>()
                        {
                            @Override
                            public void call(NetworkInfo.DetailedState detailedState)
                            {
                                ListViewCard.Item item = mItems.get(2);

                                if (detailedState != null)
                                {
                                    switch (detailedState)
                                    {
                                        case AUTHENTICATING:
                                            item.setLine2(getString(R.string.authenticating));
                                            break;

                                        case BLOCKED:
                                            item.setLine2(getString(R.string.blocked));
                                            break;

                                        case CAPTIVE_PORTAL_CHECK:
                                            item.setLine2(getString(R.string.captive_portal_check));
                                            break;

                                        case CONNECTED:
                                            item.setLine2(getString(R.string.connected));
                                            break;

                                        case CONNECTING:
                                            item.setLine2(getString(R.string.connecting));
                                            break;

                                        case DISCONNECTED:
                                            item.setLine2(getString(R.string.disconnected));
                                            break;

                                        case DISCONNECTING:
                                            item.setLine2(getString(R.string.disconnecting));
                                            break;

                                        case FAILED:
                                            item.setLine2(getString(R.string.failed));
                                            break;

                                        case IDLE:
                                            item.setLine2(getString(R.string.idle));
                                            break;

                                        case OBTAINING_IPADDR:
                                            item.setLine2(getString(R.string.obtaining_ipaddr));
                                            break;

                                        case SCANNING:
                                            item.setLine2(getString(R.string.scanning));
                                            break;

                                        case SUSPENDED:
                                            item.setLine2(getString(R.string.suspended));
                                            break;

                                        case VERIFYING_POOR_LINK:
                                            item.setLine2(getString(R.string.verifying_poor_link));
                                            break;
                                    }
                                }
                                else
                                {
                                    item.setLine2(getString(R.string.unknown));
                                }

                                mListViewCard.setItems(mItems);
                            }
                        })
        );

        mCompositeSubscription.add(
                mListViewCard.buttonClicks()
                        .subscribe(new Action1<Void>()
                        {
                            @Override
                            public void call(Void aVoid)
                            {
                                try
                                {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.project_url)));
                                    startActivity(intent);
                                }
                                catch (ActivityNotFoundException exception)
                                {
                                    Toast.makeText(getBaseContext(), R.string.you_dont_have_a_web_browser, Toast.LENGTH_LONG).show();
                                }
                            }
                        })
        );
    }

    // endregion
}