package com.bandung.disiplin.notification;

import android.content.Context;
import android.util.Log;

//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
import com.bandung.disiplin.util.PrefUtil;

/**
 * Created by yogi on 9/17/16.
 */
public class FirebaseIDService {//extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";
    Context mContext;

//    @Override
//    public void onTokenRefresh() {
//        // Get updated InstanceID token.
////        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
////        Log.d(TAG, "Refreshed token: " + refreshedToken);
////
////        // TODO: Implement this method to send any registration to your app's servers.
////        sendRegistrationToServer(refreshedToken);
//    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
//        PrefUtil.saveString(getApplicationContext(), "token", token);
    }
}
