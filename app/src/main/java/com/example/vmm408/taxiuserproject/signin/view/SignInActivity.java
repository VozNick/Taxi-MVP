package com.example.vmm408.taxiuserproject.signin.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.map.MapActivity;
import com.example.vmm408.taxiuserproject.profile.view.ProfileActivity;
import com.example.vmm408.taxiuserproject.signin.google.GoogleSignInImpl;
import com.example.vmm408.taxiuserproject.signin.presenter.SignInPresenterImpl;
import com.example.vmm408.taxiuserproject.signin.model.SignInModelImpl;
import com.example.vmm408.taxiuserproject.signin.presenter.SignInPresenter;
import com.example.vmm408.taxiuserproject.utils.MyKeys;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity implements SignInView {
    private SignInPresenter signInPresenter;
    private ProgressDialog progressDialog;
//    private GoogleSignIn googleSignIn;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_google_sign_in);
        ButterKnife.bind(this);
        createDialog();
//        googleSignIn = new GoogleSignInImpl(this);
        if (signInPresenter == null) {
            signInPresenter = new SignInPresenterImpl(this, new SignInModelImpl(), new GoogleSignInImpl(this));
        }
    }

    private void createDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_msg));
        progressDialog.setCancelable(false);
    }

    @OnClick(R.id.sign_in_button)
    void btnSignIn() {
        signInPresenter.onClickSignIn();
    }

    @Override
    public void showLoading(boolean flag) {
        if (flag) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Subscribe
    public void getEvent(Intent intent) {
        startActivityForResult(intent, MyKeys.SIGN_IN_KEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyKeys.SIGN_IN_KEY) {
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
//            googleSignIn.setUserData(result.getSignInAccount());
            signInPresenter.resultIsSuccess(true);
        } else {
            signInPresenter.resultIsSuccess(false);
        }
    }

    @Override
    public void onConnectionFailed() {
        makeToast(getResources().getString(R.string.toast_connection_failed));
    }

    @Override
    public void onResultFailed() {
        makeToast(getResources().getString(R.string.toast_result_failed));
    }

    @Override
    public void navigateToProfileActivity() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    public void navigateToMapActivity() {
        startActivity(new Intent(this, MapActivity.class));
    }

    protected void makeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onDestroy() {
        signInPresenter.onDestroy();
        super.onDestroy();
    }
}
