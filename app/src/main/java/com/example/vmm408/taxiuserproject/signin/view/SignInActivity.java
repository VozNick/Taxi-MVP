package com.example.vmm408.taxiuserproject.signin.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.map.MapActivity;
import com.example.vmm408.taxiuserproject.profile.ProfileActivity;
import com.example.vmm408.taxiuserproject.signin.presenter.GoogleSignInPresenterImpl;
import com.example.vmm408.taxiuserproject.signin.model.SignInModelImpl;
import com.example.vmm408.taxiuserproject.signin.presenter.GoogleSignInPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity implements SignInView {
    private ProgressDialog progressDialog;
    private GoogleSignInPresenter googleSignInPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        googleSignInPresenter.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_google_sign_in);
        ButterKnife.bind(this);
        initProgressDialog();
        if (googleSignInPresenter == null) {
            googleSignInPresenter = new GoogleSignInPresenterImpl(this, new SignInModelImpl());
        }
        googleSignInPresenter.createGoogleClient(this);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_msg));
        progressDialog.setCancelable(false);
    }

    @OnClick(R.id.sign_in_button)
    void signIn() {

        Log.d("tag", "1");
        googleSignInPresenter.signIn(this);
    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onConnectionFailedListener() {
        makeToast(getResources().getString(R.string.toast_connection_failed));
    }

    @Override
    public void onResultFailed() {
        makeToast("result failed");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleSignInPresenter.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void navigateToProfileActivity(String userId, String userPhotoUrl, String userFullName) {
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
    protected void onStop() {
        super.onStop();
        googleSignInPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        googleSignInPresenter.onDestroy();
        super.onDestroy();
    }
}
