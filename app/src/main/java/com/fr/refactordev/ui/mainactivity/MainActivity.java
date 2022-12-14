package com.fr.refactordev.ui.mainactivity;

import android.Manifest;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.library.baseAdapters.BR;

import com.fr.refactordev.R;
import com.fr.refactordev.base.BaseActivity;
import com.fr.refactordev.data.preferances.prefs.PreferencesHelper;
import com.fr.refactordev.databinding.ActivityMainBinding;
import com.fr.refactordev.di.component.ActivityComponent;
import com.fr.refactordev.utils.applogger.AppLogger;

import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, ActivityMainViewModel> implements Navigator {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    public PreferencesHelper helper;

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public int getBindingVariable() {
        return BR.viewmodel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        viewModel.setNavigator(this);
        binding.setViewmodel(viewModel);


        binding.btnPrint.setOnClickListener(v -> {

        });

       // requestSinglePermissionsSafely(Manifest.permission.READ_EXTERNAL_STORAGE);
        requestMultiplePermissionsSafely(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});

        viewModel.getAddressData().observe(this,data->{
            System.out.println(data);
        });



    }

    @Override
    protected void onPermissionResult(Map<String, Boolean> result) {
        super.onPermissionResult(result);
        if (Boolean.TRUE.equals(result.get(Manifest.permission.READ_EXTERNAL_STORAGE))) {
            AppLogger.e(Manifest.permission.READ_EXTERNAL_STORAGE + " Permission Granted ");
        }else {
            AppLogger.e(Manifest.permission.READ_EXTERNAL_STORAGE + " Permission Denied ");
        }
    }



    public void showAlertDialog(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(R.string.dialog_tip);
                dialog.setMessage(msg);
                dialog.setNegativeButton(R.string.dialog_back, null);
                dialog.show();
            }
        });
    }


    @Override
    public void onGO(View v) {
        System.out.println("On-GO "+ v);
    }

    @Override
    public void onBack() {
        System.out.println("On-Back");
    }
}