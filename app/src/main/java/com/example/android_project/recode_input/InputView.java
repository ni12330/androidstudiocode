package com.example.android_project.recode_input;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android_project.R;

public class InputView extends LinearLayout {

    TextView checkbox_val;

    public InputView(Context context) {
        super(context);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.input_item, this, true);

        checkbox_val = findViewById(R.id.checkbox_val);
    }

    public void setCheckbox_val(String text){
        checkbox_val.setText(text);
    }
}
