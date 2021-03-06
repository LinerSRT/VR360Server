package ru.liner.vr360server.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.net.Socket;

import ru.liner.vr360server.Core;
import ru.liner.vr360server.activity.IDataReceiver;
import ru.liner.vr360server.activity.IServer;
import ru.liner.vr360server.activity.MainActivity;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 08.05.2022, воскресенье
 **/
public abstract class BaseFragment extends Fragment implements IDataReceiver {
    protected static final String TAG = BaseFragment.class.getSimpleName();
    protected IServer server;
    protected View parentView;

    public BaseFragment(IServer server) {
        this.server = server;
    }

    public BaseFragment() {
        this.server = MainActivity.getServer();
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        server.registerDataReceiver(this);
        onFragmentCreated();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        server.unregisterDataReceiver(this);
    }


    @CallSuper
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutRes(), container, false);
        declareViews(parentView);
        return parentView;
    }

    @Override
    public void onClientConnected(Socket socket) {

    }

    @Override
    public void onClientDisconnected(Socket socket) {

    }

    @Override
    public void onClientDataReceived(Socket socket, @NonNull String data) {

    }

    public abstract void declareViews(View view);
    public abstract void onFragmentCreated();
    @LayoutRes
    public abstract int getLayoutRes();

    @NonNull
    public Context getContext(){
        if(parentView == null)
            return getApplicationContext();
        Context context = parentView.getContext();
        return context != null ? context : getApplicationContext();
    }

    @NonNull
    public Context getApplicationContext(){
        return Core.getContext();
    }

    public <T extends View> T find(@IdRes int id){
        return parentView.findViewById(id);
    }
}
