package com.nst.yourname.miscelleneious.chromecastfeature;

import android.view.Menu;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.media.widget.ExpandedControllerActivity;
import com.nst.yourname.R;

public class ExpandedControlsActivity extends ExpandedControllerActivity {
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.expanded_controller, menu);
        CastButtonFactory.setUpMediaRouteButton(this, menu, (int) R.id.media_route_menu_item);
        return true;
    }
}
