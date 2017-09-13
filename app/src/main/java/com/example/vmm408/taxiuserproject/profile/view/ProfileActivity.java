package com.example.vmm408.taxiuserproject.profile.view;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.login.view.LoginActivity;
import com.example.vmm408.taxiuserproject.map.view.MapActivity;
import com.example.vmm408.taxiuserproject.profile.model.ProfileModelImpl;
import com.example.vmm408.taxiuserproject.profile.presenter.ProfilePresenter;
import com.example.vmm408.taxiuserproject.profile.presenter.ProfilePresenterImpl;
import com.example.vmm408.taxiuserproject.utils.BitmapUtils;
import com.example.vmm408.taxiuserproject.utils.ImageLoader;
import com.example.vmm408.taxiuserproject.utils.keys.MyKeys;

import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static com.example.vmm408.taxiuserproject.utils.keys.MyKeys.FULL_NAME_KEY;
import static com.example.vmm408.taxiuserproject.utils.keys.MyKeys.USER_ID_KEY;

public class ProfileActivity extends AppCompatActivity
        implements ProfileView {
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
    private AlertDialog alertDialog;
    private AlertDialog.OnClickListener onClickAvatarMenu =
            (dialogInterface, i) -> profilePresenter.onSelectedAvatarMenu(i);
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener onClickAgeWidget =
            (datePicker, i, i1, i2) -> profilePresenter.onSelectedDate(i, i1, i2);
    private String userId;
    private String tempAvatarString;

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
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                MyKeys.READ_STORAGE_KEY);
    }

    @Override
    public void showDataCreateProfile() {
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString(USER_ID_KEY);
        etFullName.setText(bundle.getString(FULL_NAME_KEY));
    }

    @Override
    public void showDataInWidgets(String avatar,
                                  String fullName,
                                  String phone,
                                  String age) {
        imageUserAvatar.setImageBitmap(BitmapUtils.getStringToBitmap(avatar));
        etFullName.setText(fullName);
        etPhone.setText(phone);
        tAge.setText(age);
    }

    @OnClick(R.id.image_user_avatar)
    public void onClickAvatar() {
        alertDialog = createDialog();
        profilePresenter.onClickAvatar();
    }

    private AlertDialog createDialog() {
        return new AlertDialog.Builder(this)
                .setItems(R.array.menu_new_avatar, onClickAvatarMenu)
                .create();
    }

    @Override
    public void showAvatarMenuDialog() {
        alertDialog.show();
    }

    @Override
    public void showAvatarFromCamera(int key) {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null), key);
    }

    @Override
    public void showAvatarFromGallery(int key) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), key);
    }

    @Override
    public void showDefaultAvatar() {
        imageUserAvatar.setImageResource(R.drawable.ic_person_black_24dp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == MyKeys.IMAGE_CAPTURE_KEY) {
            imageUserAvatar.setImageBitmap((Bitmap) data.getExtras().get("data"));
            tempAvatarString = BitmapUtils.getFileToString((Bitmap) data.getExtras().get("data"));
        } else if (requestCode == MyKeys.PICK_PHOTO_KEY) {
            ImageLoader.loadImage(this, data.getData(), imageUserAvatar);
            File file = new File(ImageLoader.getPathFromUri(this, data.getData()));
            Bitmap bitmap = new Compressor(this).compressToBitmap(file);
            tempAvatarString = BitmapUtils.getFileToString(bitmap);
        }
    }

    @OnClick(R.id.text_age)
    void onClickAgeWidget() {
        datePickerDialog = getWindowDateDialog();
        profilePresenter.onClickAgeWidget();
    }

    private DatePickerDialog getWindowDateDialog() {
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(this,
                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                onClickAgeWidget,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void showDatePickerDialog() {
        datePickerDialog.show();
    }

    @Override
    public void showAge(String dateOfBirth) {
        tAge.setText(dateOfBirth);
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getAvatar() {
        return tempAvatarString;
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
        finish();
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
        finish();
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
