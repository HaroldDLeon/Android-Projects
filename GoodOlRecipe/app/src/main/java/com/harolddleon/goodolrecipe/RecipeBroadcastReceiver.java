package com.harolddleon.goodolrecipe;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class RecipeBroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast received", Toast.LENGTH_SHORT).show();
        Intent activity_intent = new Intent(context, MainActivity.class);
        context.startActivity(activity_intent);
    }
}
