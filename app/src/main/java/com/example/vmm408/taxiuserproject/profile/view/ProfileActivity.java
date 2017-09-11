package com.example.vmm408.taxiuserproject.profile.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.dialogs.CustomAlertDialog;
import com.example.vmm408.taxiuserproject.login.view.LoginActivity;
import com.example.vmm408.taxiuserproject.map.MapActivity;
import com.example.vmm408.taxiuserproject.profile.model.ProfileModelImpl;
import com.example.vmm408.taxiuserproject.profile.presenter.ProfilePresenter;
import com.example.vmm408.taxiuserproject.profile.presenter.ProfilePresenterImpl;
import com.example.vmm408.taxiuserproject.utils.ImageLoader;
import com.example.vmm408.taxiuserproject.utils.keys.MyKeys;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
        implements ProfileView, AlertDialog.OnClickListener {
    @BindView(R.id.image_user_avatar)
    CircleImageView imageUserAvatar;
    @BindView(R.id.edit_text_full_name)
    EditText etFullName;
    @BindView(R.id.edit_text_phone)
    EditText etPhone;
    @BindView(R.id.spinner_gender)
    Spinner spGender;
    @BindView(R.id.text_age)
    TextView tAge;
    private ProfilePresenter profilePresenter;
    private ImageView tempAvatar;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_save_profile);
        ButterKnife.bind(this);
        if (!selfPermissionGranted()) {
            requestPermissions();
        }
        if (profilePresenter == null) {
            profilePresenter = new ProfilePresenterImpl(this, new ProfileModelImpl());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean selfPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MyKeys.READ_STORAGE_KEY);
    }

    @Override
    public void fillDataToWidgets(String avatar,
                                  String fullName,
                                  String phone,
                                  String age) {
        ImageLoader.loadImage(this, avatar, imageUserAvatar);
        ImageLoader.loadImage(this, avatar, tempAvatar);
        etFullName.setText(fullName);
        etPhone.setText(phone);
        tAge.setText(age);
    }

    @OnClick(R.id.image_user_avatar)
    public void avatarUser() {
        new CustomAlertDialog(this).showAvatarMenuDialog();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        profilePresenter.onClickAvatar(i);
    }

    @Override
    public void setAvatar(int key) {
        if (key == MyKeys.IMAGE_CAPTURE_KEY) {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null), key);
        } else if (key == MyKeys.PICK_PHOTO_KEY) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), key);
        } else if (key == MyKeys.DELETE_PHOTO_KEY) {
            imageUserAvatar.setImageResource(R.drawable.ic_person_black_24dp);
            tempAvatar.setImageResource(R.drawable.ic_person_black_24dp);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == MyKeys.IMAGE_CAPTURE_KEY) {
            imageUserAvatar.setImageBitmap((Bitmap) data.getExtras().get("data"));
            tempAvatar.setImageBitmap((Bitmap) data.getExtras().get("data"));
        } else if (requestCode == MyKeys.PICK_PHOTO_KEY) {
            ImageLoader.loadImage(this, data.getData(), imageUserAvatar);
            ImageLoader.loadImage(this, data.getData(), tempAvatar);
        }
    }

    @OnClick(R.id.text_age)
    void initAgeWidget() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                ((view1, year, month, dayOfMonth) -> profilePresenter.validateAge(dayOfMonth, (month + 1), year)),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getWindow();
        datePickerDialog.show();
    }

    @Override
    public void setAge(String dateOfBirth) {
        tAge.setText(dateOfBirth);
    }

    @Override
    public String getAvatar() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        tempAvatar.getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public String getFullName() {
        return etFullName.getText().toString();
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public String getGender() {
        return spGender.getSelectedItem().toString();
    }

    @Override
    public String getAge() {
        return tAge.getText().toString();
    }

    @OnClick(R.id.btn_save_profile)
    void btnSaveProfile() {
        profilePresenter.onClickSaveProfile();
    }

    @OnClick(R.id.btn_cancel_profile)
    void btnCancelProfile() {
        profilePresenter.onClickCancel();
    }

    @Override
    public void navigateToMapActivity() {
        startActivity(new Intent(this, MapActivity.class));
    }

    @Override
    public void showEmptyFieldsError() {
        etFullName.setError(getResources().getString(R.string.text_error_empty_fields));
    }

    @Override
    public void showWrongPhoneError() {
        etPhone.setError(getResources().getString(R.string.text_error_phone_format));
    }

    @Override
    public void navigateToSignInActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void makeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        profilePresenter.onDestroy();
        super.onDestroy();
    }
}
