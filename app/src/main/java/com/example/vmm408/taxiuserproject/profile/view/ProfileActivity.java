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

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.map.view.MapActivity;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.profile.model.ProfileModelImpl;
import com.example.vmm408.taxiuserproject.profile.presenter.ProfilePresenter;
import com.example.vmm408.taxiuserproject.profile.presenter.ProfilePresenterImpl;
import com.example.vmm408.taxiuserproject.utils.DialogUtil;
import com.example.vmm408.taxiuserproject.utils.BitmapUtil;
import com.example.vmm408.taxiuserproject.utils.DatePickerDialogUtil;
import com.example.vmm408.taxiuserproject.utils.ImageLoaderUtil;
import com.example.vmm408.taxiuserproject.constants.MyKeys;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.vmm408.taxiuserproject.constants.MyKeys.FULL_NAME_KEY;
import static com.example.vmm408.taxiuserproject.constants.MyKeys.USER_ID_KEY;

public class ProfileActivity extends AppCompatActivity implements ProfileView {
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
    @BindString(R.string.text_error_empty_fields)
    String textErrorEmptyFields;
    @BindString(R.string.text_error_phone_format)
    String textErrorPhoneFormat;
    private ProfilePresenter profilePresenter;
    private AlertDialog.OnClickListener onAvatarMenuClick =
            (dialogInterface, i) -> profilePresenter.onAvatarMenuSelected(i);
    private DatePickerDialog.OnDateSetListener onAgeWidgetClick =
            (datePicker, i, i1, i2) -> profilePresenter.onDateSelected(i, i1, i2);
    private AlertDialog.OnClickListener onConfirmExitClick =
            (dialogInterface, i) -> profilePresenter.onConfirmExitClick();
    private String userId;
    private String avatarString;
    private Intent activityResultAvatar;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_save_profile);
        ButterKnife.bind(this);
        profilePresenter = new ProfilePresenterImpl(this, new ProfileModelImpl());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean selfPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                MyKeys.READ_STORAGE_KEY);
    }

    @Override
    public void showDataToCreateProfile() {
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString(USER_ID_KEY);
        etFullName.setText(bundle.getString(FULL_NAME_KEY));
    }

    @Override
    public void showDataToEditProfile(UserModel userModel) {
        userId = userModel.getIdUser();
        avatarString = userModel.getAvatarUser();
        imageUserAvatar.setImageBitmap(BitmapUtil.getStringToBitmap(userModel.getAvatarUser()));
        etFullName.setText(userModel.getFullNameUser());
        etPhone.setText(userModel.getPhoneUser());
        tAge.setText(userModel.getAgeUser());
    }

    @OnClick(R.id.image_user_avatar)
    public void onAvatarClick() {
        profilePresenter.onAvatarClick();
    }

    @Override
    public void showAvatarMenuDialog() {
        DialogUtil.menuAvatar(this, onAvatarMenuClick).show();
    }

    @Override
    public void getAvatarFromCamera(int key) {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null), key);
    }

    @Override
    public void getAvatarFromGallery(int key) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), key);
    }

    @Override
    public void showDefaultAvatar() {
        imageUserAvatar.setImageResource(R.drawable.ic_person_black_24dp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            this.activityResultAvatar = data;
            profilePresenter.onActivityResult(requestCode);
        }
    }

    @Override
    public void showAvatarFromCamera() {
        imageUserAvatar.setImageBitmap((Bitmap) activityResultAvatar.getExtras().get("data"));
        avatarString = BitmapUtil.getFileToString((Bitmap) activityResultAvatar.getExtras().get("data"));
    }

    @Override
    public void showAvatarFromGallery() {
        ImageLoaderUtil.loadImage(this, activityResultAvatar.getData(), imageUserAvatar);
        Bitmap bitmap = BitmapUtil.compressImageFile(this, activityResultAvatar.getData());
        avatarString = BitmapUtil.getFileToString(bitmap);
    }

    @OnClick(R.id.text_age)
    void onAgeWidgetClick() {
        profilePresenter.onAgeWidgetClick();
    }

    @Override
    public void showDatePickerDialog() {
        DatePickerDialogUtil.userAge(this, onAgeWidgetClick).show();
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
        return avatarString;
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
    void onSaveProfileClick() {
        profilePresenter.onSaveProfileClick();
    }

    @OnClick(R.id.btn_cancel_profile)
    void onCancelProfileClick() {
        profilePresenter.onCancelProfileClick();
    }

    @Override
    public void navigateToMapActivity() {
        startActivity(new Intent(this, MapActivity.class));
        finish();
    }

    @Override
    public void showEmptyFieldsError() {
        etFullName.setError(textErrorEmptyFields);
    }

    @Override
    public void showWrongPhoneError() {
        etPhone.setError(textErrorPhoneFormat);
    }

    @Override
    public void showConfirmExitDialog() {
        DialogUtil.confirmExitApp(this, onConfirmExitClick).show();
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void onBackPressed() {
        profilePresenter.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        profilePresenter.onDestroy();
        super.onDestroy();
    }
}
