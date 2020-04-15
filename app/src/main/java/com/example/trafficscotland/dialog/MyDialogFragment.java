package com.example.trafficscotland.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.trafficscotland.R;

import java.util.ArrayList;



public class MyDialogFragment extends DialogFragment {
    int mNum;

    ArrayList<Spanned> ArrayList = new ArrayList<Spanned>();

    android.widget.ListView ListView;

    ArrayAdapter<Spanned> ArrayAdapter;

    TextView TextView; Button Button;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static MyDialogFragment newInstance(int num,  Bundle args) {
        MyDialogFragment f = new MyDialogFragment();


        // Supply num input as an argument.
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marker_info, container, false);

        ListView = (android.widget.ListView) view.findViewById(R.id.incidents_feed);

        final Bundle bdl = getArguments();

        String title, description;

        Button = (Button) view.findViewById(R.id.button);
        TextView = (TextView) view.findViewById(R.id.editText);

        Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               getDialog().dismiss();
            }
        });

        try
        {

            int count = bdl.getInt("count");


            if(count == 1) {
                title = bdl.getString("title");
                description = bdl.getString("description");

                ArrayList.add(Html.fromHtml("<b>" + title + "</b><br /><div style='padding: 1px;'>" + description + "</div>"));
                TextView.setText("1 Item");
            }else{
                ArrayList<String> ArrayList2 = new ArrayList<String>();

                ArrayList2 = bdl.getStringArrayList("ArrayList2");

                int counter = 0;
                for (String item : ArrayList2) {
                    counter ++;
                }

                TextView.setText(counter + " Items");

                for (String item : ArrayList2) {
                    ArrayList.add(Html.fromHtml(item));
                }
            }
        }
        catch(final Exception e)
        {
            System.out.println("DIALOG ERROR::" + e.getMessage());
        }

        ArrayAdapter = new ArrayAdapter<Spanned>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                ArrayList
        );

        ListView.setAdapter(ArrayAdapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        }
    }
}