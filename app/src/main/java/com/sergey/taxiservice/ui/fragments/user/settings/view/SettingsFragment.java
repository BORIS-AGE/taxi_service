package com.sergey.taxiservice.ui.fragments.user.settings.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.databinding.FragmentSettingsBinding;
import com.sergey.taxiservice.ui.fragments.user.settings.presenter.SettingsPresenter;
import com.sergey.taxiservice.util.ImageLoader;
import com.sergey.taxiservice.util.MediaUtils;
import com.sergey.taxiservice.util.UiUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.File;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SettingsFragment extends BaseBindingToolbarFragment<SettingsPresenter, FragmentSettingsBinding> implements SettingsView, View.OnClickListener {

    public static final int ACTION_GALLERY = 1;
    public static final int ACTION_CAMERA = 2;

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 70;

    private ArrayAdapter<String> ageAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_settings;
    }

    @Override
    public String getTitle() {
        return getString(R.string.settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSave.setOnClickListener(this);
        binding.tvShowHidePassword.setOnClickListener(this);
        binding.btnAddAvatar.setOnClickListener(this);
        binding.btnDeleteAvatar.setOnClickListener(this);

        String[] ages = new String[MAX_AGE - MIN_AGE];
        for(int i = MIN_AGE; i < MAX_AGE; i++) {
            ages[i - MIN_AGE] = Integer.toString(i);
        }

        if(getActivity() != null) {
            ageAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, ages);
            ageAdapter.setDropDownViewResource(R.layout.item_spinner);
            binding.age.setAdapter(ageAdapter);

            ArrayAdapter<?> genderAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.genders, R.layout.item_spinner);
            genderAdapter.setDropDownViewResource(R.layout.item_spinner);
            binding.gender.setAdapter(genderAdapter);
        }

        presenter.loadUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                hideInputMethod();
                presenter.saveChanges();
                break;

            case R.id.tv_show_hide_password:
                if(binding.etPassword.getTransformationMethod() instanceof PasswordTransformationMethod)
                    binding.etPassword.setTransformationMethod(new SingleLineTransformationMethod());
                else binding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
                break;

            case R.id.btn_add_avatar:{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle(R.string.photo);
                alertDialogBuilder.setItems(new CharSequence[]{getString(R.string.from_gallery), getString(R.string.take_a_shot_camera)},
                        (dialog, which) -> {
                            switch (which){
                                case 0: {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), ACTION_GALLERY);
                                    break;
                                }
                                case 1: {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaUtils.getSetupAvatarUri(getActivity()));
                                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                        startActivityForResult(intent, ACTION_CAMERA);
                                    }
                                    break;
                                }
                            }
                        });
                alertDialogBuilder.show();
                break;
            }

            case R.id.btn_delete_avatar:{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage(getString(R.string.delete_confirmation_photo));
                alertDialogBuilder.setPositiveButton(R.string.yes, ((dialog, which) -> presenter.deleteAvatar()));
                alertDialogBuilder.setNegativeButton(R.string.no, null);
                alertDialogBuilder.show();
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ACTION_GALLERY:
                if(resultCode == Activity.RESULT_OK) {
                    cropImage(data.getData());
                }
                break;

            case ACTION_CAMERA:
                if(resultCode == Activity.RESULT_OK && getActivity() != null) {
                    cropImage(MediaUtils.getSetupAvatarUri(getActivity()));
                }
                break;

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                new File(MediaUtils.getSetupAvatarUri(getActivity()).getPath()).delete();
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if(resultCode == Activity.RESULT_OK) {
                    presenter.saveAvatar(result.getUri());
                    binding.ivAvatar.setImageURI(result.getUri());
                }
        }
    }

    @Override
    public String getPhoneNumber() {
        return binding.etPhoneNumber.getText().toString();
    }

    @Override
    public String getPassword() {
        return binding.etPassword.getText().toString();
    }

    @Override
    public String getFirstName() {
        return binding.etFirstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return binding.etLastName.getText().toString();
    }

    @Override
    public String getPatronymic() {
        return binding.etPatronymic.getText().toString();
    }

    @Override
    public String getAbout() {
        return binding.about.getText().toString();
    }

    @Override
    public int getAge() {
        return Integer.parseInt(binding.age.getSelectedItem().toString());
    }

    @Override
    public int getGender() {
        return binding.gender.getSelectedItemPosition();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        binding.etPhoneNumber.setText(phoneNumber);
    }

    @Override
    public void setPassword(String password) {
        binding.etPassword.setText(password);
    }

    @Override
    public void setFirstName(String firstName) {
        binding.etFirstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        binding.etLastName.setText(lastName);
    }

    @Override
    public void setPatronymic(String patronymic) {
        binding.etPatronymic.setText(patronymic);
    }

    @Override
    public void setAbout(String about) {
        binding.about.setText(about);
    }

    @Override
    public void setAge(int age) {
        int position = ageAdapter.getPosition(Integer.toString(age));
        binding.age.setSelection(position);
    }

    @Override
    public void setGender(int gender) {
        binding.gender.setSelection(gender);
    }

    @Override
    public void setAvatar(String avatar) {
        if(avatar != null && !avatar.isEmpty()) {
            ImageLoader.load(avatar, binding.ivAvatar, R.drawable.default_avatar, null);
        } else ImageLoader.load(R.drawable.default_avatar, binding.ivAvatar, null);
    }

    @Override
    public void onPhoneNumberError() {
        binding.etPhoneNumber.setError(getString(R.string.field_error_cannot_be_empty));
        showErrorMessage();
    }

    @Override
    public void onPasswordError() {
        binding.etPassword.setError(getString(R.string.field_error_cannot_be_empty));
        showErrorMessage();
    }

    @Override
    public void onFirstNameError() {
        binding.etFirstName.setError(getString(R.string.field_error_cannot_be_empty));
        showErrorMessage();
    }

    @Override
    public void onLastNameError() {
        binding.etLastName.setError(getString(R.string.field_error_cannot_be_empty));
        showErrorMessage();
    }

    @Override
    public void onAvaDeleted() {
        onEditSuccess();
        ImageLoader.load(R.drawable.default_avatar, binding.ivAvatar, null);
    }

    @Override
    public void onEditSuccess() {
        showMessage(getString(R.string.saved));
    }

    @Override
    public void onEditFailed(JSONObject errors) {
        String unknownError = getString(R.string.error_unknown_short);
        UiUtils.setErrorsIfExist(errors, "phone_number", binding.etPhoneNumber, unknownError);
        UiUtils.setErrorsIfExist(errors, "password", binding.etPassword, unknownError);
        UiUtils.setErrorsIfExist(errors, "surname", binding.etLastName, unknownError);
        UiUtils.setErrorsIfExist(errors, "name", binding.etFirstName, unknownError);
        UiUtils.setErrorsIfExist(errors, "patronymic", binding.etPatronymic, unknownError);
        UiUtils.setErrorsIfExist(errors, "about", binding.about, unknownError);
    }

    private void showErrorMessage() {
        showMessage("Не удалось сохранить");
    }

    private void cropImage(Uri uri) {
        if (getActivity() != null) {
            CropImage.activity(uri)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1, 1)
                    .setFixAspectRatio(true)
                    .start(getActivity(), this);
        }
    }

    private void hideInputMethod() {
        Activity activity = getActivity();

        if(activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();

            if(inputMethodManager != null && view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
