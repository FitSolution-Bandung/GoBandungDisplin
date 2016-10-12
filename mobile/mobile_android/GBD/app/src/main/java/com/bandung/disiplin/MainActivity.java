package com.bandung.disiplin;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bandung.disiplin.config.CONSTANT;
import com.bandung.disiplin.fragment.Dummy;
import com.bandung.disiplin.fragment.HomeFragment;
import com.bandung.disiplin.fragment.Pengaturan;
import com.bandung.disiplin.util.PrefUtil;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.mikepenz.fastadapter.utils.RecyclerViewCacheUtil;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.simako.user.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private Toolbar toolbar;
    private RelativeLayout l_linear;
    private String TAG = "MainActivity";

    private ObservableScrollView mScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.include);

        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }

        l_linear = (RelativeLayout) findViewById(R.id.l_linear);
        final IProfile profile3 = new ProfileDrawerItem().withName("Yogi").withEmail("yogieeka@gmail.com").withIcon(R.mipmap.ava_e).withIdentifier(102);
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.mipmap.shirt)
                .addProfiles(
                        profile3
                )
                .withAccountHeader(R.layout.custom_header)
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withTranslucentStatusBar(false)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.timeline).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(100),
                        new PrimaryDrawerItem().withName(R.string.lokasi).withIcon(FontAwesome.Icon.faw_map_marker),
                        new PrimaryDrawerItem().withName(R.string.bahasa).withIcon(FontAwesome.Icon.faw_language),
                        new PrimaryDrawerItem().withName(R.string.favorite).withIcon(FontAwesome.Icon.faw_star).withIdentifier(4).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
//                        new ExpandableDrawerItem().withName("Menu Lainya").withIcon(GoogleMaterial.Icon.gmd_more).withSubItems(
//                                new SecondaryDrawerItem().withName("Hubungi Kami").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_phone),
//                                new SecondaryDrawerItem().withName("Tentang Aplikasi").withLevel(2).withIcon(FontAwesome.Icon.faw_question)
//                        ),
                        new PrimaryDrawerItem().withName(R.string.pengaturan).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(16).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.logout).withIcon(GoogleMaterial.Icon.gmd_info).withIdentifier(15).withSelectable(false)

                )


                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        Fragment fragment = new Fragment();
                        Log.d("position", String.valueOf(position));
                        switch (position - 1) {
                            case 0:
                                fragment = new HomeFragment();
                                toolbar.setTitle("Home");
                                break;
                            case 1:
                                fragment = new Dummy();
                                toolbar.setTitle("Lokasi Kita");
                                break;
                            case 2:
                                fragment = new Dummy();
                                toolbar.setTitle("Bahasa");
                                break;
                            case 3:
                                fragment = new Dummy();
                                toolbar.setTitle("Favorite");
                                break;
                            case 4:
                                fragment = new Dummy();
                                toolbar.setTitle("");
                                break;
                            case 5:
                                fragment = new Pengaturan();
                                toolbar.setTitle("Pengaturan");
                                break;
                            case 6:
                                fragment = new Dummy();
                                toolbar.setTitle("About");
                                break;
                        }
                        transaction.replace(R.id.l_linear, fragment);
                        transaction.commit();
                        return false;
                    }
                })

                .withSelectedItemByPosition(1)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
        new RecyclerViewCacheUtil<IDrawerItem>().withCacheSize(2).apply(result.getRecyclerView(), result.getDrawerItems());

        result.setSelection(100);
        result.updateBadge(4, new StringHolder(10 + ""));

        //sendToken();
    }

    private void sendToken() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", PrefUtil.getString(this, "token"));
            jsonObject.put("grup", "Kapolres");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("cek params -->", jsonObject.toString());


        AndroidNetworking.post(CONSTANT.BASE_URL)
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("SEND TOKEN")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // do anything with response
                        Log.d("response >", response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }





    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "sms & location services permission granted");
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}