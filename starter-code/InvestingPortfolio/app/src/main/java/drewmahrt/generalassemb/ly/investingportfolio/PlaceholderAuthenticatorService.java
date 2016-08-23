package drewmahrt.generalassemb.ly.investingportfolio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.net.Authenticator;

public class PlaceholderAuthenticatorService extends Service {

    private PlaceholderAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new PlaceholderAuthenticator(this);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
